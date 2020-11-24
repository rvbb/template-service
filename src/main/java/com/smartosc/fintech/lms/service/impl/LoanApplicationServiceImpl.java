package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.RepaymentState;
import com.smartosc.fintech.lms.common.util.DateTimeUtil;
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
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.smartosc.fintech.lms.common.constant.LoanApplicationStatus.ACTIVE;
import static com.smartosc.fintech.lms.common.constant.LoanApplicationStatus.DROP_OFF;
import static com.smartosc.fintech.lms.common.constant.LoanTransactionType.FUNDING;

@Service
@AllArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    private RepaymentRepository repaymentRepository;

    private RepaymentService repaymentService;

    private LoanTransactionRepository loanTransactionRepository;

    private static final int LEAD_DAY=3;

    @Override
    public LoanApplicationDto findLoanApplicationEntityByUuid(String uuid) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(
                () -> new EntityNotFoundException("Not found loan application with uuid : " + uuid));
        LoanApplicationDto loanApplicationDto = LoanApplicationMapper.INSTANCE.mapToDto(loanApplicationEntity);

        /*set outstandingBalance
        * oustandingBalance khac 0 voi khoan vay da duoc giai ngan
        *  va bang 0 doi voi cac khoan vay chua gia ngan*/
        BigDecimal outstandingBalance = new BigDecimal(0);
        if (loanApplicationEntity.getStatus().equals(ACTIVE.getValue())) {
            if (loanApplicationEntity.getPrincipalPaid() != null)
                outstandingBalance = outstandingBalance.subtract(loanApplicationEntity.getPrincipalPaid());
            else
                outstandingBalance = loanApplicationEntity.getLoanAmount();
        }
        loanApplicationDto.setOutstandingBalance(BigDecimalMapper.mapToScale(outstandingBalance));
        loanApplicationDto.setLoanType(loanApplicationEntity.getLoanProduct().getName());

        /*set expire date*/
        LoanTransactionEntity loanTransactionEntity =
                loanTransactionRepository.findDistinctFirstByLoanApplicationUuidAndType(uuid, FUNDING.name());
        LocalDateTime expireDate = null;
        if (loanTransactionEntity != null) {
            Period period = Period.ofDays(Integer.parseInt(loanApplicationEntity.getLoanTerm()));
            expireDate = loanTransactionEntity.getEntryDate().toLocalDateTime().plus(period);
            Timestamp expireTimestamp = Timestamp.valueOf(expireDate);
            loanApplicationDto.setExpireDate(DateTimeUtil.getFormatTimestamp(expireTimestamp));
        }

        /*set interest accrued
        * lai suat cong don chi tinh voi khoan vay da duoc giai ngan*/
        if (loanApplicationEntity.getStatus() == ACTIVE.getValue()&&loanTransactionEntity != null) {
                BigDecimal interestAccrued = repaymentService.calculateAccruedInterest(loanApplicationEntity, loanTransactionEntity.getEntryDate());
                loanApplicationDto.setInterestAccrued(BigDecimalMapper.mapToScale(interestAccrued));
        }

        /*set payment amount*/
        List<RepaymentEntity> repaymentEntities =
                repaymentRepository.findByLoanApplicationUuidAndStateNotOrderByDueDateAsc(uuid, RepaymentState.PAID.name());
        if (!repaymentEntities.isEmpty()&&expireDate != null) {
            LocalDateTime currentDate = LocalDateTime.now();
            long diffSeconds = Duration.between(currentDate,expireDate).getSeconds();
            if (diffSeconds<=LEAD_DAY*24*60*60) {
                RepaymentEntity latestPayment = repaymentEntities.get(0);
                PaymentAmountDto paymentAmountDto = PaymentAmountMapper.INSTANCE.entityToDto(latestPayment);
                loanApplicationDto.setPaymentAmount(paymentAmountDto);
            }
        }
        return loanApplicationDto;
    }

    @Override
    public List<BriefLoanDto> findLoanApplicationByUser(long id) {
        List<LoanApplicationEntity> loanApplicationEntities = loanApplicationRepository.findLoanApplicationEntityByUserIdAndStatusNot(id, DROP_OFF.getValue());
        List<BriefLoanDto> briefLoanDtos = new ArrayList<>();
        for (LoanApplicationEntity loanApplicationEntity : loanApplicationEntities) {
            BriefLoanDto briefLoanDto = BriefLoanMapper.INSTANCE.mapToDto(loanApplicationEntity);
            BigDecimal outstandingBalance = loanApplicationEntity.getLoanAmount();
            if (loanApplicationEntity.getPrincipalPaid() != null)
                outstandingBalance = outstandingBalance.subtract(loanApplicationEntity.getPrincipalPaid());

            briefLoanDto.setOutstandingBalance(BigDecimalMapper.mapToScale(outstandingBalance));
            briefLoanDtos.add(briefLoanDto);
        }
        return briefLoanDtos;
    }

}
