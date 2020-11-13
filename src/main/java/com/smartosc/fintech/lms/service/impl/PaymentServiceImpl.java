package com.smartosc.fintech.lms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.common.constant.PaymentGatewayStatus;
import com.smartosc.fintech.lms.common.constant.PaymentHistoryStatus;
import com.smartosc.fintech.lms.config.ApplicationConfig;
import com.smartosc.fintech.lms.dto.PaymentRequest;
import com.smartosc.fintech.lms.dto.PaymentResponse;
import com.smartosc.fintech.lms.dto.PaymentResultDto;
import com.smartosc.fintech.lms.dto.RepayRequestInPaymentServiceDto;
import com.smartosc.fintech.lms.entity.PaymentHistoryEntity;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import com.smartosc.fintech.lms.repository.PaymentHistoryRepository;
import com.smartosc.fintech.lms.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private static final String TIMEOUT_MESSAGE = "Call payment gateway timeout";
    private static final String FAIL_MESSAGE = "Call payment gateway fail";

    private RestTemplate restTemplate;
    private ApplicationConfig applicationConfig;
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    public PaymentServiceImpl(@Qualifier("RT") RestTemplate restTemplate,
                              ApplicationConfig applicationConfig,
                              PaymentHistoryRepository paymentRepository) {
        this.restTemplate = restTemplate;
        this.applicationConfig = applicationConfig;
        this.paymentHistoryRepository = paymentRepository;
    }

    @Override
    public void processFunding(PaymentRequest paymentRequest) {
        PaymentHistoryEntity history = new PaymentHistoryEntity();
        Timestamp creationDate = new Timestamp(new Date().getTime());
        history.setCreationDate(creationDate);
        history.setUuid(UUID.randomUUID().toString());
        history.setAmount(paymentRequest.getAmount());
        history.setBody(convertObject(paymentRequest));
        history.setUrl(applicationConfig.getPaymentGatewayUrl());

        try {
            ResponseEntity<PaymentResponse> response =
                    restTemplate.postForEntity(applicationConfig.getPaymentGatewayUrl(), paymentRequest, PaymentResponse.class);
            history.setResponse(convertObject(response));
            history.setStatus(PaymentHistoryStatus.SUCCESS.getValue());
            paymentHistoryRepository.save(history);
        } catch (ResourceAccessException ex) {
            history.setStatus(PaymentHistoryStatus.TIMEOUT.getValue());
            history.setMessage(ex.getMessage());
            paymentHistoryRepository.save(history);
            throw new BusinessServiceException(TIMEOUT_MESSAGE, ErrorCode.PAYMENT_GATEWAY_TIMEOUT);
        } catch (Exception ex) {
            history.setStatus(PaymentHistoryStatus.FAIL.getValue());
            history.setMessage(ex.getMessage());
            paymentHistoryRepository.save(history);
            throw new BusinessServiceException(FAIL_MESSAGE, ErrorCode.PAYMENT_GATEWAY_FAIL);
        }
    }

    private String convertObject(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @Override
    public PaymentResultDto processRepayLoan(RepayRequestInPaymentServiceDto repayRequestInPaymentServiceDto) {
        PaymentResultDto paymentResultDto = new PaymentResultDto();
        PaymentHistoryEntity history = new PaymentHistoryEntity();
        Timestamp creationDate = new Timestamp(new Date().getTime());
        history.setCreationDate(creationDate);
        history.setUuid(UUID.randomUUID().toString());
        history.setAmount(repayRequestInPaymentServiceDto.getAmount());
        history.setBody(convertObject(repayRequestInPaymentServiceDto));
        history.setUrl(applicationConfig.getRepaymentGatewayUrl());

        try {
            ResponseEntity<PaymentResponse> response =
                    restTemplate.postForEntity(applicationConfig.getRepaymentGatewayUrl(), repayRequestInPaymentServiceDto, PaymentResponse.class);
            history.setResponse(convertObject(response));
            paymentResultDto.setData(response.getBody());
            if (response.getStatusCodeValue() >= HttpStatus.BAD_REQUEST.value()) {
                history.setStatus(PaymentHistoryStatus.FAIL.getValue());
                paymentHistoryRepository.save(history);
                paymentResultDto.setFailed(true);
                throw new BusinessServiceException(FAIL_MESSAGE, ErrorCode.PAYMENT_GATEWAY_FAIL);
            }
            if (response.getBody().getStatus().getCode() != PaymentGatewayStatus.SUCCESS.getValue()) {
                history.setStatus(PaymentHistoryStatus.FAIL.getValue());
                paymentHistoryRepository.save(history);
                paymentResultDto.setFailed(true);
                throw new BusinessServiceException(FAIL_MESSAGE, ErrorCode.PAYMENT_GATEWAY_FAIL);
            }
            history.setStatus(PaymentHistoryStatus.SUCCESS.getValue());
            paymentHistoryRepository.save(history);
            paymentResultDto.setSuccessful(true);
        } catch (ResourceAccessException ex) {
            //handle connection timeout
            history.setStatus(PaymentHistoryStatus.TIMEOUT.getValue());
            history.setMessage(ex.getMessage());
            paymentHistoryRepository.save(history);
            throw new BusinessServiceException(TIMEOUT_MESSAGE, ErrorCode.PAYMENT_GATEWAY_TIMEOUT);
        } catch (Exception ex) {
            history.setStatus(PaymentHistoryStatus.FAIL.getValue());
            history.setMessage(ex.getMessage());
            paymentHistoryRepository.save(history);
            throw new BusinessServiceException(FAIL_MESSAGE, ErrorCode.PAYMENT_GATEWAY_FAIL);
        }
        return paymentResultDto;
    }
}
