package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.BankAccountType;
import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.common.constant.LoanApplicationStatus;
import com.smartosc.fintech.lms.common.constant.LoanTransactionType;
import com.smartosc.fintech.lms.dto.*;
import com.smartosc.fintech.lms.entity.BankAccount;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.LoanTransactionRepository;
import com.smartosc.fintech.lms.service.PaymentService;
import com.smartosc.fintech.lms.service.RepaymentService;
import com.smartosc.fintech.lms.service.mapper.LoanTransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import com.smartosc.fintech.lms.repository.RepaymentRepository;
import com.smartosc.fintech.lms.service.mapper.RepaymentMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RepaymentServiceImpl implements RepaymentService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanTransactionRepository loanTransactionRepository;
    private final PaymentService paymentGatewayService;
    private final RepaymentRepository repaymentRepository;

    @Override
    public RepaymentResponseDto repayLoan(RepaymentRequestDto repaymentRequestDto) {
        Optional<LoanApplicationEntity> existedLoanApplication = loanApplicationRepository.findLoanApplicationEntityByUuid(
                repaymentRequestDto.getUuid());
        LoanApplicationEntity loanApplicationEntity = existedLoanApplication.orElseThrow(EntityNotFoundException::new);
        validateData(repaymentRequestDto, loanApplicationEntity);
        LoanTransactionEntity loanTransactionEntity = null;
        RepayRequestInPaymentServiceDto repayRequestInPaymentServiceDto = buildRepayRequestInPaymentServiceDto(
                repaymentRequestDto, loanApplicationEntity);
        PaymentResultDto paymentResultDto = paymentGatewayService.processRepayLoan(repayRequestInPaymentServiceDto);
        if (paymentResultDto.isSuccessful()) {
            closeLoanApplication(loanApplicationEntity);
            loanTransactionEntity = saveRepaymentLoanTransaction(repaymentRequestDto, loanApplicationEntity);
        }
        RepaymentResponseDto repaymentResponseDto = new RepaymentResponseDto();
        repaymentResponseDto.setLoanTransactionDto(LoanTransactionMapper.INSTANCE.mapToDto(loanTransactionEntity));
        return repaymentResponseDto;
    }

    private void validateData(RepaymentRequestDto repaymentRequestDto, LoanApplicationEntity loanApplicationEntity){
        if(loanApplicationEntity.getStatus() == LoanApplicationStatus.CLOSE.getValue()){
            throw new BusinessServiceException("Loan was close already!", ErrorCode.LOAN_APPLICATION_CLOSE_ALREADY);
        }
    }

    private RepayRequestInPaymentServiceDto buildRepayRequestInPaymentServiceDto(RepaymentRequestDto repaymentRequestDto,
                                                                                 LoanApplicationEntity loanApplicationEntity){
        RepayRequestInPaymentServiceDto repayRequestInPaymentServiceDto = new RepayRequestInPaymentServiceDto();
        Collection<BankAccount> bankAccounts = loanApplicationEntity.getBankAccounts();
        if(bankAccounts != null && bankAccounts.size() > 0){
            BankAccount lenderBankAccount = bankAccounts.stream()
                    .filter(b -> b.getType() == BankAccountType.TYPE_LENDER.getValue())
                    .iterator().next();
            if(lenderBankAccount != null){
                repayRequestInPaymentServiceDto.setReceivedAccount(lenderBankAccount.getAccount());
                repayRequestInPaymentServiceDto.setReceivedBank(lenderBankAccount.getBankCode());
            }

            BankAccount borrowerBankAccount = bankAccounts.stream()
                    .filter(b -> b.getType() == BankAccountType.TYPE_BORROWER.getValue())
                    .iterator().next();
            if(borrowerBankAccount != null){
                repayRequestInPaymentServiceDto.setSendAccount(borrowerBankAccount.getAccount());
                repayRequestInPaymentServiceDto.setSendBank(borrowerBankAccount.getBankCode());
            }
        }

        repayRequestInPaymentServiceDto.setApplicationUuid(UUID.randomUUID().toString());
        repayRequestInPaymentServiceDto.setAmount(repaymentRequestDto.getTotalMoney());
        repayRequestInPaymentServiceDto.setMessage("Repay loan " + repaymentRequestDto.getUuid());
        return repayRequestInPaymentServiceDto;
    }


    private void closeLoanApplication(LoanApplicationEntity loanApplicationEntity) {
        loanApplicationEntity.setStatus(LoanApplicationStatus.CLOSE.getValue());
        loanApplicationRepository.save(loanApplicationEntity);
    }

    private LoanTransactionEntity saveRepaymentLoanTransaction(RepaymentRequestDto repaymentRequestDto,
                                                               LoanApplicationEntity loanApplicationEntity) {
        LoanTransactionEntity loanTransactionEntity = createNewRepaymentLoanTransaction(
                repaymentRequestDto, loanApplicationEntity);
        loanTransactionRepository.save(loanTransactionEntity);
        return loanTransactionEntity;
    }

    private LoanTransactionEntity createNewRepaymentLoanTransaction(RepaymentRequestDto repaymentRequestDto,
                                                                    LoanApplicationEntity loanApplicationEntity) {
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        LoanTransactionEntity loanTransactionEntity = new LoanTransactionEntity();
        UUID uuid = UUID.randomUUID();
        loanTransactionEntity.setUuid(uuid.toString());
        loanTransactionEntity.setAmount(repaymentRequestDto.getPrincipalAmount());
        loanTransactionEntity.setCreationDate(currentTimeStamp);
        loanTransactionEntity.setFeesAmount(new BigDecimal(0));
        loanTransactionEntity.setInterestAmount(repaymentRequestDto.getInterestAmount());
        loanTransactionEntity.setInterestRate(new BigDecimal(loanApplicationEntity.getInterestRate()));
        loanTransactionEntity.setPenaltyAmount(new BigDecimal(0));
        loanTransactionEntity.setPrincipalAmount(repaymentRequestDto.getPrincipalAmount());
        loanTransactionEntity.setTaxOnFeesAmount(new BigDecimal(0));
        loanTransactionEntity.setTaxOnInterestAmount(new BigDecimal(0));
        loanTransactionEntity.setTaxOnPenaltyAmount(new BigDecimal(0));
        loanTransactionEntity.setType(LoanTransactionType.REPAYMENT.name());
        loanTransactionEntity.setUser(loanApplicationEntity.getUser());
        loanTransactionEntity.setLoanApplication(loanApplicationEntity);

        return loanTransactionEntity;
    }

    @Override
    public List<RepaymentDto> calculate(LoanApplicationDto loanApplicationDto) {
        return calculate(loanApplicationDto.getUuid());
    }

    @Override
    @SMFLogger
    public List<RepaymentDto> calculate(String loanApplicationUuid) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(loanApplicationUuid);
        LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(EntityNotFoundException::new);

        List<RepaymentEntity> repaymentEntities = calculate(loanApplicationEntity);
        repaymentEntities = repaymentRepository.saveAll(repaymentEntities);

        return repaymentEntities.stream().map(RepaymentMapper.INSTANCE::entityToDto).collect(Collectors.toList());
    }

    private List<RepaymentEntity> calculate(LoanApplicationEntity loanApplicationEntity) {
        BigDecimal interestDue = calculateInterestDue(
                loanApplicationEntity.getInterestDue(),
                loanApplicationEntity.getApproveDate()
        );

        RepaymentEntity repaymentEntity = new RepaymentEntity();
        repaymentEntity.setUuid(UUID.randomUUID().toString());
        repaymentEntity.setInterestDue(interestDue);
        repaymentEntity.setPrincipalDue(loanApplicationEntity.getPrincipalDue());
        repaymentEntity.setUser(loanApplicationEntity.getUser());
        repaymentEntity.setLoanApplication(loanApplicationEntity);
        return Collections.singletonList(repaymentEntity);
    }

    private BigDecimal calculateInterestDue(BigDecimal interestDue, Timestamp approveDate) {
        BigDecimal daysOfYear = new BigDecimal(LocalDateTime.now().getDayOfYear());
        BigDecimal interestDueOfDay = interestDue.divide(daysOfYear, RoundingMode.CEILING);
        BigDecimal diffDays = getDiffDays(approveDate);
        return interestDueOfDay.multiply(diffDays);
    }

    private BigDecimal getDiffDays(Timestamp approveDate) {
        LocalDateTime startDate = approveDate.toLocalDateTime();
        LocalDateTime currentDay = LocalDateTime.now();
        long diffDays = Duration.between(currentDay, startDate).toDays();
        return BigDecimal.valueOf(diffDays);
    }
}
