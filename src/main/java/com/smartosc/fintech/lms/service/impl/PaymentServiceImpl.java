package com.smartosc.fintech.lms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.common.constant.PaymentHistoryStatus;
import com.smartosc.fintech.lms.config.ApplicationConfig;
import com.smartosc.fintech.lms.dto.PaymentRequest;
import com.smartosc.fintech.lms.dto.PaymentResponse;
import com.smartosc.fintech.lms.entity.PaymentHistoryEntity;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import com.smartosc.fintech.lms.repository.PaymentHistoryRepository;
import com.smartosc.fintech.lms.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private RestTemplate restTemplate;
    private ApplicationConfig applicationConfig;
    private PaymentHistoryRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(@Qualifier("RT") RestTemplate restTemplate,
                              ApplicationConfig applicationConfig,
                              PaymentHistoryRepository paymentRepository) {
        this.restTemplate = restTemplate;
        this.applicationConfig = applicationConfig;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void makePayment(PaymentRequest paymentRequest) {
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

            if (response.getStatusCodeValue() >= HttpStatus.BAD_REQUEST.value()) {
                history.setStatus(PaymentHistoryStatus.FAIL.getValue());
                paymentRepository.save(history);
                throw new BusinessServiceException("Call payment gateway fail", ErrorCode.PAYMENT_GATEWAY_FAIL);
            }

            history.setStatus(PaymentHistoryStatus.SUCCESS.getValue());
            paymentRepository.save(history);
        } catch (Exception ex) {
            history.setStatus(PaymentHistoryStatus.FAIL.getValue());
            history.setMessage(ex.getMessage());
            paymentRepository.save(history);
            throw new BusinessServiceException("Call payment gateway fail", ErrorCode.PAYMENT_GATEWAY_FAIL);
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
}
