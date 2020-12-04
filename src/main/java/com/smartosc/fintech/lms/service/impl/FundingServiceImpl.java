package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.BankAccountType;
import com.smartosc.fintech.lms.common.constant.LoanApplicationStatus;
import com.smartosc.fintech.lms.common.constant.LoanTransactionType;
import com.smartosc.fintech.lms.dto.FundingRequest;
import com.smartosc.fintech.lms.dto.PaymentRequest;
import com.smartosc.fintech.lms.dto.RepaymentDto;
import com.smartosc.fintech.lms.entity.BankAccount;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.LoanTransactionRepository;
import com.smartosc.fintech.lms.service.FundingService;
import com.smartosc.fintech.lms.service.PaymentService;
import com.smartosc.fintech.lms.service.RepaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FundingServiceImpl implements FundingService {
    private final LoanApplicationRepository applicationRepository;
    private final LoanTransactionRepository transactionRepository;
    private final PaymentService paymentService;
    private final RepaymentService repaymentService;

    @Override
    @Transactional
    public LoanTransactionEntity makeFunding(FundingRequest request) {
        Optional<LoanApplicationEntity> existApplication = applicationRepository.findByUuid(request.getApplicationUuid());
        LoanApplicationEntity application = existApplication.orElseThrow(() -> new EntityNotFoundException("Loan application not found"));
        if (application.getStatus() != null && application.getStatus() != LoanApplicationStatus.SIGN.getValue()) {
            throw new EntityNotFoundException("Loan application not found");
        }

        PaymentRequest paymentRequest = createPaymentRequest(application);
        paymentService.processFunding(paymentRequest);

        LoanTransactionEntity transaction = createTransactionEntity(application);
        transactionRepository.save(transaction);

        List<RepaymentDto> repaymentDtos = generateRepaymentPlan(application);
        BigDecimal principalDue = repaymentDtos.stream()
                .map(RepaymentDto::getPrincipalDue).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal interestDue = repaymentDtos.stream()
                .map(RepaymentDto::getInterestDue).reduce(BigDecimal.ZERO, BigDecimal::add);

        application.getLoanTransactions().add(transaction);
        application.setStatus(LoanApplicationStatus.ACTIVE.getValue());
        application.setPrincipalDue(principalDue);
        application.setInterestDue(interestDue);
        applicationRepository.save(application);

        return transaction;
    }

    private LoanTransactionEntity createTransactionEntity(LoanApplicationEntity application) {
        LoanTransactionEntity transaction = new LoanTransactionEntity();

        Timestamp currentDate = new Timestamp(new Date().getTime());
        transaction.setCreationDate(currentDate);
        transaction.setEntryDate(currentDate);

        transaction.setUuid(UUID.randomUUID().toString());
        transaction.setLoanApplication(application);
        transaction.setType(LoanTransactionType.FUNDING.name());
        transaction.setAmount(application.getLoanAmount());

        return transaction;
    }

    private PaymentRequest createPaymentRequest(LoanApplicationEntity application) {
        PaymentRequest request = new PaymentRequest();
        request.setApplicationUuid(application.getUuid());
        request.setAmount(application.getLoanAmount());

        BankAccount receivedAccount = application.getBankAccounts()
                .stream()
                .filter(account -> BankAccountType.TYPE_BORROWER.getValue().equals(account.getType()))
                .findFirst().orElseGet(BankAccount::new);
        request.setReceivedAccount(receivedAccount.getAccount());
        request.setReceivedBank(receivedAccount.getBankCode());

        BankAccount sendAccount = application.getBankAccounts()
                .stream()
                .filter(account -> BankAccountType.TYPE_LENDER.getValue().equals(account.getType()))
                .findFirst().orElseGet(BankAccount::new);
        request.setSendAccount(sendAccount.getAccount());
        request.setSendBank(sendAccount.getBankCode());

        String message = String.format("Disbursement for loan %s", application.getUuid());
        request.setMessage(message);

        return request;
    }

    private List<RepaymentDto> generateRepaymentPlan(LoanApplicationEntity application) {
        return repaymentService.calculate(application.getUuid());
    }
}
