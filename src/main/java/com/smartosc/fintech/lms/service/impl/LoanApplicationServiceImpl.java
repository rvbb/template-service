package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.PaymentAmountDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.RepaymentRepository;
import com.smartosc.fintech.lms.service.LoanApplicationService;
import com.smartosc.fintech.lms.service.RepaymentService;
import com.smartosc.fintech.lms.service.mapper.LoanApplicationMapper;
import com.smartosc.fintech.lms.service.mapper.PaymentAmountMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
        if (loanApplicationEntity.getPrincipalPaid() != null) {
            outstandingBalance = outstandingBalance.subtract(loanApplicationEntity.getPrincipalPaid());
            loanApplicationDto.setOutstanding_balance(outstandingBalance);
        }
        loanApplicationDto.setLoanType(loanApplicationEntity.getLoanProduct().getName());


        RepaymentEntity latestPayment = repaymentRepository.findRepaymentOfLoanOrderByDuedate(uuid).get(0);
        PaymentAmountDto paymentAmountDto = PaymentAmountMapper.INSTANCE.entityToDto(latestPayment);
        paymentAmountDto.setInterest(repaymentService.calculateAccruedInterest(loanApplicationEntity));
        loanApplicationDto.setPaymentAmount(paymentAmountDto);
        return loanApplicationDto;
    }

    @Override
    public List<LoanApplicationDto> findLoanApplicationByUser(long id) {
        List<LoanApplicationDto> loanApplicationDtos = new ArrayList<>();
        List<LoanApplicationEntity> loanApplicationEntities = loanApplicationRepository.findLoanApplicationEntityByUserId(id);
        for (LoanApplicationEntity loanApplication : loanApplicationEntities) {
            loanApplicationDtos.add(LoanApplicationMapper.INSTANCE.mapToDto(loanApplication));
        }
        return loanApplicationDtos;
    }

}
