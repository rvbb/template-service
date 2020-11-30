package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.RepaymentState;
import com.smartosc.fintech.lms.common.util.DateTimeUtil;
import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.dto.BriefLoanDto;
import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.PaymentAmountDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.LoanTransactionRepository;
import com.smartosc.fintech.lms.repository.RepaymentRepository;
import com.smartosc.fintech.lms.service.LoanApplicationService;
import com.smartosc.fintech.lms.service.RepaymentService;
import com.smartosc.fintech.lms.service.mapper.BigDecimalMapper;
import com.smartosc.fintech.lms.service.mapper.BriefLoanMapper;
import com.smartosc.fintech.lms.service.mapper.LoanApplicationMapper;
import com.smartosc.fintech.lms.service.mapper.PaymentAmountMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.smartosc.fintech.lms.common.constant.LoanApplicationStatus.ACTIVE;
import static com.smartosc.fintech.lms.common.constant.LoanTransactionType.FUNDING;
import static com.smartosc.fintech.lms.common.constant.ParamsConstant.LEAD_DAY;
import static com.smartosc.fintech.lms.common.constant.ParamsConstant.DAY_OF_YEAR;

@Service
@AllArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    private RepaymentRepository repaymentRepository;

    private RepaymentService repaymentService;

    private LoanTransactionRepository loanTransactionRepository;


    @Override
    @SMFLogger
    public LoanApplicationDto findLoanApplicationEntityByUuid(String uuid) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(
                () -> new EntityNotFoundException("Not found loan application with uuid : " + uuid));
        LoanApplicationDto loanApplicationDto = LoanApplicationMapper.INSTANCE.mapToDto(loanApplicationEntity);

        /*set outstandingBalance*/
        BigDecimal outstandingBalance = BigDecimal.ZERO;
        if (ACTIVE.getValue() == (loanApplicationEntity.getStatus())) {
            outstandingBalance = loanApplicationEntity.getPrincipalPaid() != null
                    ? loanApplicationEntity.getLoanAmount().subtract(loanApplicationEntity.getPrincipalPaid())
                    : loanApplicationEntity.getLoanAmount();
        }
        loanApplicationDto.setOutstandingBalance(BigDecimalMapper.mapToScale(outstandingBalance));
        loanApplicationDto.setLoanType(loanApplicationEntity.getLoanProduct().getName());

        /*set expire date*/
        LoanTransactionEntity loanTransactionEntity =
                loanTransactionRepository.findDistinctFirstByLoanApplicationUuidAndType(uuid, FUNDING.name());
        LocalDateTime expireDate = null;
        if (loanTransactionEntity != null) {
            Period period = Period.ofDays(Integer.parseInt(loanApplicationEntity.getLoanTerm()));
            LocalDateTime entryDate = loanTransactionEntity.getEntryDate().toLocalDateTime().toLocalDate().atTime(LocalTime.MAX);
            expireDate = entryDate.plus(period);
            loanApplicationDto.setExpireDate(DateTimeUtil.getFormatTimestamp(Timestamp.valueOf(expireDate)));
        }

        /*set interest accrued*/
        if (ACTIVE.getValue() == loanApplicationEntity.getStatus() && loanTransactionEntity != null) {
            BigDecimal interestAccrued = repaymentService.calculateAccruedInterest(loanApplicationEntity, loanTransactionEntity.getEntryDate());
            loanApplicationDto.setInterestAccrued(BigDecimalMapper.mapToScale(interestAccrued));
        }

        /*set payment amount*/
        List<RepaymentEntity> repaymentEntities =
                repaymentRepository.findByLoanApplicationUuidAndStateNotOrderByDueDateAsc(uuid, RepaymentState.PAID.name());
        if (!repaymentEntities.isEmpty()) {
            LocalDateTime currentDate = LocalDateTime.now();
            Period periodLead = Period.ofDays(LEAD_DAY);
            if (expireDate != null && currentDate.plus(periodLead).compareTo(expireDate.toLocalDate().atStartOfDay()) >= 0) {
                RepaymentEntity latestPayment = calculateInterest(LocalDate.now(), repaymentEntities.get(0), loanApplicationDto.getInterestRate());
                PaymentAmountDto paymentAmountDto = PaymentAmountMapper.INSTANCE.entityToDto(latestPayment);
                loanApplicationDto.setPaymentAmount(paymentAmountDto);
            }
        }
        return loanApplicationDto;
    }

    @Override
    @SMFLogger
    public List<BriefLoanDto> findLoanApplicationByUser(long id) {
        List<LoanApplicationEntity> loanApplicationEntities = loanApplicationRepository.findLoanApplicationEntityByUserIdAndStatusNotDrop(id);
        List<BriefLoanDto> briefLoanDtos = new ArrayList<>();
        for (LoanApplicationEntity loanApplicationEntity : loanApplicationEntities) {
            BriefLoanDto briefLoanDto = BriefLoanMapper.INSTANCE.mapToDto(loanApplicationEntity);
            BigDecimal outstandingBalance = BigDecimal.ZERO;
            if (ACTIVE.getValue() == (loanApplicationEntity.getStatus())) {
                outstandingBalance = loanApplicationEntity.getPrincipalPaid() != null
                        ? loanApplicationEntity.getLoanAmount().subtract(loanApplicationEntity.getPrincipalPaid())
                        : loanApplicationEntity.getLoanAmount();
            }
            briefLoanDto.setOutstandingBalance(BigDecimalMapper.mapToScale(outstandingBalance));
            briefLoanDtos.add(briefLoanDto);
        }
        return briefLoanDtos;
    }

    public RepaymentEntity calculateInterest(LocalDate currentDate, RepaymentEntity latestPayment, BigDecimal interestRate) {
        LocalDate dueDate = latestPayment.getDueDate().toLocalDateTime().toLocalDate();
        if (currentDate.isBefore(dueDate)) {
            return latestPayment;
        }

        long diffDays = ChronoUnit.DAYS.between(dueDate, currentDate);
        BigDecimal expiredInterest = latestPayment.getPrincipalDue()
                .multiply(BigDecimal.valueOf(diffDays))
                .multiply(interestRate)
                .divide(DAY_OF_YEAR, RoundingMode.CEILING
                );
        latestPayment.setInterestDue(latestPayment.getInterestDue().add(expiredInterest));
        return latestPayment;
    }

}
