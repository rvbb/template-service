package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.BankAccountType;
import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.common.constant.LoanApplicationStatus;
import com.smartosc.fintech.lms.common.constant.LoanTransactionType;
import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.dto.*;
import com.smartosc.fintech.lms.entity.BankAccount;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.LoanTransactionRepository;
import com.smartosc.fintech.lms.repository.RepaymentRepository;
import com.smartosc.fintech.lms.service.PaymentService;
import com.smartosc.fintech.lms.service.RepaymentService;
import com.smartosc.fintech.lms.service.mapper.LoanTransactionMapper;
import com.smartosc.fintech.lms.service.mapper.RepaymentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
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
        RepaymentEntity repaymentEntity = repaymentRepository.findFirstByUuid(repaymentRequestDto.getUuid()).orElseThrow(() -> new EntityNotFoundException("no Repayment found (id): " + repaymentRequestDto.getUuid()));
        LoanApplicationEntity loanApplicationEntity = repaymentEntity.getLoanApplication();
        validateData(repaymentRequestDto, loanApplicationEntity);
        LoanTransactionEntity loanTransactionEntity = null;
        RepayRequestInPaymentServiceDto repayRequestInPaymentServiceDto = buildRepayRequestInPaymentServiceDto(
                repaymentRequestDto, loanApplicationEntity);
        PaymentResultDto paymentResultDto = paymentGatewayService.processRepayLoan(repayRequestInPaymentServiceDto);
        if (paymentResultDto.isSuccessful()) {
            calculateAndSaveRepayment(repaymentRequestDto, repaymentEntity);
            closeLoanApplication(loanApplicationEntity);
            loanTransactionEntity = saveRepaymentLoanTransaction(repaymentRequestDto, repaymentEntity);
        }

        RepaymentResponseDto repaymentResponseDto = new RepaymentResponseDto();
        closeLoanApplication(loanApplicationEntity);
        repaymentResponseDto.setLoanTransactionDto(LoanTransactionMapper.INSTANCE.mapToDto(loanTransactionEntity));
        return repaymentResponseDto;
    }

    private void validateData(RepaymentRequestDto repaymentRequestDto, LoanApplicationEntity loanApplicationEntity) {
        if (loanApplicationEntity.getStatus() == LoanApplicationStatus.CLOSE.getValue()) {
            throw new BusinessServiceException("Loan was close already!", ErrorCode.LOAN_APPLICATION_CLOSE_ALREADY);
        }
    }

    private RepayRequestInPaymentServiceDto buildRepayRequestInPaymentServiceDto(RepaymentRequestDto repaymentRequestDto,
                                                                                 LoanApplicationEntity loanApplicationEntity) {
        RepayRequestInPaymentServiceDto repayRequestInPaymentServiceDto = new RepayRequestInPaymentServiceDto();
        Collection<BankAccount> bankAccounts = loanApplicationEntity.getBankAccounts();
        if (bankAccounts != null && !bankAccounts.isEmpty()) {
            BankAccount lenderBankAccount = bankAccounts.stream()
                    .filter(b -> BankAccountType.TYPE_LENDER.getValue().equals(b.getType()) )
                    .iterator().next();
            if (lenderBankAccount != null) {
                repayRequestInPaymentServiceDto.setReceivedAccount(lenderBankAccount.getAccount());
                repayRequestInPaymentServiceDto.setReceivedBank(lenderBankAccount.getBankCode());
            }

            BankAccount borrowerBankAccount = bankAccounts.stream()
                    .filter(b -> BankAccountType.TYPE_BORROWER.getValue().equals(b.getType()))
                    .iterator().next();
            if (borrowerBankAccount != null) {
                repayRequestInPaymentServiceDto.setSendAccount(borrowerBankAccount.getAccount());
                repayRequestInPaymentServiceDto.setSendBank(borrowerBankAccount.getBankCode());
            }
        }

        repayRequestInPaymentServiceDto.setApplicationUuid(UUID.randomUUID().toString());
        repayRequestInPaymentServiceDto.setAmount(repaymentRequestDto.getAmount());
        repayRequestInPaymentServiceDto.setMessage("Repay loan " + repaymentRequestDto.getUuid());
        return repayRequestInPaymentServiceDto;
    }


    private void closeLoanApplication(LoanApplicationEntity loanApplicationEntity) {
        loanApplicationEntity.setStatus(LoanApplicationStatus.CLOSE.getValue());
        loanApplicationRepository.save(loanApplicationEntity);
    }

    private LoanTransactionEntity saveRepaymentLoanTransaction(RepaymentRequestDto repaymentRequestDto, RepaymentEntity repaymentEntity) {
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        LoanTransactionEntity loanTransactionEntity = new LoanTransactionEntity();
        UUID uuid = UUID.randomUUID();
        loanTransactionEntity.setUuid(uuid.toString());
        loanTransactionEntity.setAmount(repaymentRequestDto.getAmount());
        loanTransactionEntity.setCreationDate(currentTimeStamp);
        loanTransactionEntity.setFeesAmount(new BigDecimal(0));
        loanTransactionEntity.setInterestAmount(repaymentEntity.getInterestPaid());
        loanTransactionEntity.setInterestRate(new BigDecimal(repaymentEntity.getLoanApplication().getInterestRate()));
        loanTransactionEntity.setPenaltyAmount(new BigDecimal(0));
        loanTransactionEntity.setPrincipalAmount(repaymentEntity.getPrincipalPaid());
        loanTransactionEntity.setTaxOnFeesAmount(new BigDecimal(0));
        loanTransactionEntity.setTaxOnInterestAmount(new BigDecimal(0));
        loanTransactionEntity.setTaxOnPenaltyAmount(new BigDecimal(0));
        loanTransactionEntity.setType(LoanTransactionType.REPAYMENT.name());
        loanTransactionEntity.setUser(repaymentEntity.getUser());
        loanTransactionEntity.setLoanApplication(repaymentEntity.getLoanApplication());
        loanTransactionRepository.save(loanTransactionEntity);
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

    @Override
    public RepaymentDto get(String id) {
        RepaymentEntity repaymentEntity = repaymentRepository.findFirstByUuid(id).orElseThrow(() -> new EntityNotFoundException("entity not found"));
        RepaymentDto dto = RepaymentMapper.INSTANCE.entityToDto(repaymentEntity);
        dto.setTotalAmount(dto.getPrincipalDue().add(dto.getInterestDue()).add(dto.getFeeDue()));
        return (dto);
    }

    private List<RepaymentEntity> calculate(LoanApplicationEntity loanApplicationEntity) {
        BigDecimal interestDue = calculateInterestDue(
                loanApplicationEntity.getLoanAmount(),
                new BigDecimal(loanApplicationEntity.getInterestRate()),
                loanApplicationEntity.getApproveDate()
        );

        RepaymentEntity repaymentEntity = new RepaymentEntity();
        repaymentEntity.setUuid(UUID.randomUUID().toString());
        repaymentEntity.setInterestDue(interestDue);
        repaymentEntity.setPrincipalDue(loanApplicationEntity.getLoanAmount());
        repaymentEntity.setUser(loanApplicationEntity.getUser());
        repaymentEntity.setLoanApplication(loanApplicationEntity);
        return Collections.singletonList(repaymentEntity);
    }

    private BigDecimal calculateInterestDue(BigDecimal loanAmount, BigDecimal interestRate, Timestamp approveDate) {
        BigDecimal diffDays = getDiffDays(approveDate);
        return interestRate.multiply(loanAmount).multiply(diffDays);
    }

    private BigDecimal getDiffDays(Timestamp approveDate) {
        LocalDateTime startDate = approveDate.toLocalDateTime();
        LocalDateTime currentDay = LocalDateTime.now();
        long diffDays = Duration.between(startDate, currentDay).toDays();
        return BigDecimal.valueOf(diffDays + 1);
    }

    public void calculateAndSaveRepayment(RepaymentRequestDto repaymentRequestDto, RepaymentEntity repaymentEntity) {
        /**
         * Just an example to calculate a Loan, this method will be refactor in the future when more Loan Applications Type comes
         */
        this.calculateAmount(repaymentRequestDto, repaymentEntity);
        Timestamp current = new Timestamp(System.currentTimeMillis());
        repaymentEntity.setLastPaidDate(current);
        repaymentEntity.setLastPenaltyAppliedDate(current);
        /**Carefully: this repayment just set repaid date in this MVP,
         * in the future, the repayment will be updated when all repayments were paid
          */
        repaymentEntity.setRepaidDate(current);
        LoanApplicationEntity loanApplicationEntity = repaymentEntity.getLoanApplication();
        if (loanApplicationEntity.getPrincipalPaid() == null) {
            loanApplicationEntity.setPrincipalPaid(new BigDecimal("0"));
        }
        if (loanApplicationEntity.getInterestPaid() == null) {
            loanApplicationEntity.setInterestPaid(new BigDecimal("0"));
        }
        if (loanApplicationEntity.getFeePaid() == null) {
            loanApplicationEntity.setFeePaid(new BigDecimal("0"));
        }
        loanApplicationEntity.setPrincipalPaid(loanApplicationEntity.getPrincipalPaid().add(repaymentEntity.getPrincipalDue()));
        BigDecimal interestPaid = repaymentRequestDto.getAmount().subtract(repaymentEntity.getPrincipalDue());
        loanApplicationEntity.setInterestPaid(loanApplicationEntity.getInterestPaid().add(interestPaid));
        repaymentEntity.setInterestPaid(interestPaid);
        repaymentRepository.save(repaymentEntity);
    }

    private void calculateAmount(RepaymentRequestDto repaymentRequestDto, RepaymentEntity repaymentEntity) {
        BigDecimal remainAmount = repaymentRequestDto.getAmount();
        if (repaymentEntity.getFeeDue() != null) {
            remainAmount = repaymentRequestDto.getAmount().subtract(repaymentEntity.getFeeDue());
        }

        repaymentEntity.setFeePaid(repaymentEntity.getFeeDue());

        if (remainAmount.compareTo(repaymentEntity.getPrincipalDue()) < 1) {
            repaymentEntity.setPrincipalPaid(remainAmount);
            return;
        }

        repaymentEntity.setPrincipalPaid(repaymentEntity.getPrincipalDue());
        remainAmount = remainAmount.subtract(repaymentEntity.getPrincipalDue());

        if (remainAmount.compareTo(repaymentEntity.getInterestDue()) < 1) {
            repaymentEntity.setInterestDue(remainAmount);
            return;
        }

        repaymentEntity.setInterestPaid(repaymentEntity.getInterestDue());
        remainAmount = remainAmount.subtract(repaymentEntity.getInterestDue());
    }
}
