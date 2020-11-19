package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.RepaymentState;
import com.smartosc.fintech.lms.dto.BriefLoanDto;
import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.PaymentAmountDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.RepaymentRepository;
import com.smartosc.fintech.lms.service.LoanApplicationService;
import com.smartosc.fintech.lms.service.RepaymentService;
import com.smartosc.fintech.lms.service.mapper.BriefLoanMapper;
import com.smartosc.fintech.lms.service.mapper.LoanApplicationMapper;
import com.smartosc.fintech.lms.service.mapper.PaymentAmountMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    private RepaymentRepository repaymentRepository;

    private RepaymentService repaymentService;

    @Override
    public LoanApplicationDto findLoanApplicationEntityByUuid(String uuid) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(
                () -> new EntityNotFoundException("Not found loan application with uuid : " + uuid));
        LoanApplicationDto loanApplicationDto = LoanApplicationMapper.INSTANCE.mapToDto(loanApplicationEntity);

        BigDecimal outstandingBalance = loanApplicationEntity.getLoanAmount();
        if (loanApplicationEntity.getPrincipalPaid() != null)
            outstandingBalance = outstandingBalance.subtract(loanApplicationEntity.getPrincipalPaid());
        loanApplicationDto.setOutstandingBalance(outstandingBalance);
        loanApplicationDto.setLoanType(loanApplicationEntity.getLoanProduct().getName());

        List<RepaymentEntity> repaymentEntities = repaymentRepository.findByLoanApplicationUuidAndStateNotOrderByDueDateAsc(uuid, RepaymentState.PAID.name());
        if (!repaymentEntities.isEmpty()) {
            RepaymentEntity latestPayment = repaymentEntities.get(0);
            PaymentAmountDto paymentAmountDto = PaymentAmountMapper.INSTANCE.entityToDto(latestPayment);
            paymentAmountDto.setInterest(repaymentService.calculateAccruedInterest(loanApplicationEntity));
            loanApplicationDto.setPaymentAmount(paymentAmountDto);
        }
        return loanApplicationDto;
    }

    @Override
    public List<BriefLoanDto> findLoanApplicationByUser(long id) {
        List<LoanApplicationEntity> loanApplicationEntities = loanApplicationRepository.findLoanApplicationEntityByUserId(id);
        return BriefLoanMapper.INSTANCE.mapToListBriefLoanDto(loanApplicationEntities);
    }

}
