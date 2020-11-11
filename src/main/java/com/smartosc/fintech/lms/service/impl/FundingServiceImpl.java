package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.LoanApplicationStatus;
import com.smartosc.fintech.lms.common.constant.LoanTransactionType;
import com.smartosc.fintech.lms.dto.FundingRequest;
import com.smartosc.fintech.lms.dto.PaymentRequest;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.LoanTransactionRepository;
import com.smartosc.fintech.lms.service.FundingService;
import com.smartosc.fintech.lms.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FundingServiceImpl implements FundingService {
    private final LoanApplicationRepository applicationRepository;
    private final LoanTransactionRepository transactionRepository;
    private final PaymentService paymentService;

    @Override
    public void makeFunding(FundingRequest request) {
        Optional<LoanApplicationEntity> existApplication = applicationRepository.findLoanApplicationEntityByUuid(request.getApplicationUuid());
        LoanApplicationEntity application = existApplication.orElseThrow(EntityNotFoundException::new);
        if (application.getStatus() != null && application.getStatus() != LoanApplicationStatus.APPROVED.getValue()) {
            throw new EntityNotFoundException();
        }

        PaymentRequest paymentRequest = createPaymentDto();
        paymentService.makePayment(paymentRequest);

        LoanTransactionEntity transaction = createTransactionEntity(application);
        transactionRepository.save(transaction);

        application.getLoanTransactions().add(transaction);
        application.setStatus(LoanApplicationStatus.FUNDED.getValue());
        applicationRepository.save(application);
    }

    private LoanTransactionEntity createTransactionEntity(LoanApplicationEntity application) {
        LoanTransactionEntity transaction = new LoanTransactionEntity();

        Timestamp creationDate = new Timestamp(new Date().getTime());
        transaction.setCreationDate(creationDate);

        transaction.setUuid(UUID.randomUUID().toString());
        transaction.setLoanApplication(application);
        transaction.setType(LoanTransactionType.FUNDING.name());
        transaction.setAmount(application.getLoanAmount());

        return transaction;
    }

    private PaymentRequest createPaymentDto() {
        PaymentRequest paymentRequest = new PaymentRequest();

        return paymentRequest;
    }
}
