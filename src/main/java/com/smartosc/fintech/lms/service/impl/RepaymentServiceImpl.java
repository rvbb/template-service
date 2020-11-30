package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.common.constant.LoanApplicationStatus;
import com.smartosc.fintech.lms.common.constant.LoanTransactionType;
import com.smartosc.fintech.lms.common.constant.PaymentGatewayConstants;
import com.smartosc.fintech.lms.common.constant.PaymentGatewayStatus;
import com.smartosc.fintech.lms.common.constant.RepaymentState;
import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.config.ApplicationConfig;
import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.PaymentResponse;
import com.smartosc.fintech.lms.dto.RepaymentDto;
import com.smartosc.fintech.lms.dto.RepaymentRequestDto;
import com.smartosc.fintech.lms.dto.RepaymentResponseDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.LoanTransactionRepository;
import com.smartosc.fintech.lms.repository.RepaymentRepository;
import com.smartosc.fintech.lms.service.EmailService;
import com.smartosc.fintech.lms.service.RepaymentService;
import com.smartosc.fintech.lms.service.mapper.RepaymentMapper;
import com.smartosc.fintech.lms.validator.RepaymentRequestValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.smartosc.fintech.lms.common.constant.LoanTransactionType.FUNDING;
import static com.smartosc.fintech.lms.common.constant.ParamsConstant.DAY_OF_YEAR;


@Service
@AllArgsConstructor
@Slf4j
public class RepaymentServiceImpl implements RepaymentService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanTransactionRepository loanTransactionRepository;
    private final RepaymentRepository repaymentRepository;
    private final EmailService emailService;
    private final RepaymentRequestValidator repaymentRequestValidator;
    private final ApplicationConfig applicationConfig;

    @Override
    public RepaymentResponseDto payBack(RepaymentRequestDto repaymentRequestDto) {
        validateInput(repaymentRequestDto);
        RepaymentEntity repaymentEntity = repaymentRepository.findFirstByUuid(repaymentRequestDto.getUuid()).orElseThrow(() -> new EntityNotFoundException("no Repayment found (id): " + repaymentRequestDto.getUuid()));
        LoanApplicationEntity loanApplicationEntity = repaymentEntity.getLoanApplication();
        validateData(loanApplicationEntity);
        return buildRepaymentResponse(repaymentRequestDto, repaymentEntity);
    }

    private void validateInput(RepaymentRequestDto repaymentRequestDto) {
        repaymentRequestValidator.validateRepaymentRequest(repaymentRequestDto);
    }
    private void validateData(LoanApplicationEntity loanApplicationEntity){
        if (loanApplicationEntity != null && loanApplicationEntity.getStatus() == LoanApplicationStatus.CLOSE.getValue()) {
            throw new BusinessServiceException("Loan was close already!", ErrorCode.LOAN_APPLICATION_CLOSE_ALREADY);
        }
    }

    private LoanTransactionEntity processWhenRepaySuccess(RepaymentRequestDto repaymentRequestDto, RepaymentEntity repaymentEntity) {
        calculateAndSaveRepayment(repaymentRequestDto, repaymentEntity);
        closeLoanApplication(repaymentEntity.getLoanApplication());
        LoanTransactionEntity transaction = saveRepaymentLoanTransaction(repaymentRequestDto, repaymentEntity);
        emailService.sendRepaymentEmail(repaymentEntity);

        return transaction;
    }

    private String buildPaymentUrl(RepaymentRequestDto repaymentRequestDto){
        StringBuilder paymentUrlBuilder = new StringBuilder().append(applicationConfig.getRepaymentGatewayUrl());
        paymentUrlBuilder.append(PaymentGatewayConstants.PARAM_START)
                .append(PaymentGatewayConstants.AMOUNT_PARAM).append(PaymentGatewayConstants.EQUAL).append(repaymentRequestDto.getAmount().toString())
                .append(PaymentGatewayConstants.AND)
                .append(PaymentGatewayConstants.UUID_PARAM).append(PaymentGatewayConstants.EQUAL).append(repaymentRequestDto.getUuid());
        return paymentUrlBuilder.toString();

    }

    private RepaymentResponseDto buildRepaymentResponse(RepaymentRequestDto repaymentRequestDto, RepaymentEntity repaymentEntity) {
        RepaymentResponseDto repaymentResponseDto = new RepaymentResponseDto();
        repaymentResponseDto.setPaymentUrl(buildPaymentUrl(repaymentRequestDto));
        return repaymentResponseDto;
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
    public BigDecimal calculateAccruedInterest(LoanApplicationEntity loanApplicationEntity, Timestamp fundedDate) {
        return calculateInterestDue(
                loanApplicationEntity.getLoanAmount(),
                loanApplicationEntity.getInterestRate(),
                fundedDate
        );
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
                loanApplicationEntity.getLoanTerm()
        );

        RepaymentEntity repaymentEntity = new RepaymentEntity();
        repaymentEntity.setUuid(UUID.randomUUID().toString());
        repaymentEntity.setState(RepaymentState.PENDING.name());
        repaymentEntity.setInterestDue(interestDue);
        repaymentEntity.setPrincipalDue(loanApplicationEntity.getLoanAmount());
        repaymentEntity.setUser(loanApplicationEntity.getUser());
        repaymentEntity.setLoanApplication(loanApplicationEntity);

        LoanTransactionEntity transaction =
                loanTransactionRepository.findDistinctFirstByLoanApplicationUuidAndType(loanApplicationEntity.getUuid(), FUNDING.name());
        if (transaction != null) {
            Period period = Period.ofDays(Integer.parseInt(loanApplicationEntity.getLoanTerm()));
            LocalDateTime entryDate = transaction.getEntryDate().toLocalDateTime().toLocalDate().atTime(LocalTime.MAX);
            LocalDateTime dueDate = entryDate.plus(period);
            repaymentEntity.setDueDate(Timestamp.valueOf(dueDate));
        }

        return Collections.singletonList(repaymentEntity);
    }

    private BigDecimal calculateInterestDue(BigDecimal loanAmount, BigDecimal interestRate, String loanTerm) {
        return loanAmount.multiply(interestRate).multiply(BigDecimal.valueOf(Integer.parseInt(loanTerm)))
                .divide(DAY_OF_YEAR, RoundingMode.CEILING);
    }

    private BigDecimal calculateInterestDue(BigDecimal loanAmount, String interestRate, Timestamp approveDate) {
        BigDecimal interestRated = new BigDecimal(interestRate);
        BigDecimal diffDays = getDiffDays(approveDate);
        return loanAmount.multiply(interestRated).multiply(diffDays)
                .divide(DAY_OF_YEAR, RoundingMode.CEILING);
    }

    private BigDecimal getDiffDays(Timestamp approveDate) {
        LocalDateTime startDate = approveDate.toLocalDateTime();
        startDate  = LocalDateTime.of(startDate.getYear(),startDate.getMonthValue(),startDate.getDayOfMonth(),0, 0, 0, 0);
        LocalDateTime currentDay = LocalDateTime.now();
        long diffDays = Duration.between(startDate, currentDay).toDays();
        return BigDecimal.valueOf(diffDays);
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
        repaymentEntity.setState(RepaymentState.PAID.name());
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

    @Override
    public RepaymentResponseDto processPayResult(PaymentResponse paymentResponse) {
        validatePayResultInput(paymentResponse);
        RepaymentEntity repaymentEntity = repaymentRepository.findFirstByUuid(paymentResponse.getData()).orElseThrow(() -> new EntityNotFoundException("no Repayment found (id): " + paymentResponse.getData()));
        LoanApplicationEntity loanApplicationEntity = repaymentEntity.getLoanApplication();
        validatePayResult(loanApplicationEntity);
        RepaymentRequestDto repaymentRequestDto = createRepaymentRequest(repaymentEntity);
        if (isPayResultSuccess(paymentResponse)) {
            processWhenRepaySuccess(repaymentRequestDto, repaymentEntity);
        }
        return buildRepaymentResponse(repaymentRequestDto, repaymentEntity);
    }

    private boolean isPayResultSuccess(PaymentResponse paymentResponse){
        return PaymentGatewayStatus.SUCCESS.getValue() == paymentResponse.getStatus().getCode();
    }

    private void validatePayResultInput(PaymentResponse paymentResponse){
        if(paymentResponse.getData() == null || paymentResponse.getData().trim().isEmpty()){
            throw new BusinessServiceException("UUID is empty", ErrorCode.REPAYMENT_UUID_EMPTY);
        }
        if(paymentResponse.getStatus() == null){
            throw new BusinessServiceException("Status is empty", ErrorCode.REPAYMENT_RESULT_EMPTY);
        }
    }

    private void validatePayResult(LoanApplicationEntity loanApplicationEntity){
        if (loanApplicationEntity.getStatus() == LoanApplicationStatus.CLOSE.getValue()) {
            throw new BusinessServiceException("Loan was close already!", ErrorCode.LOAN_APPLICATION_CLOSE_ALREADY);
        }
    }

    private RepaymentRequestDto createRepaymentRequest(RepaymentEntity repaymentEntity){
        RepaymentRequestDto repaymentRequestDto = new RepaymentRequestDto();
        BigDecimal amount = new BigDecimal(0);
        if(repaymentEntity.getInterestDue() != null){
            amount = amount.add(repaymentEntity.getInterestDue());
        }
        if(repaymentEntity.getPrincipalDue() != null){
            amount = amount.add(repaymentEntity.getPrincipalDue());
        }
        repaymentRequestDto.setAmount(amount);
        repaymentRequestDto.setUuid(repaymentEntity.getUuid());
        return repaymentRequestDto;
    }
}
