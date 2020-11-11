package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.LoanApplicationStatus;
import com.smartosc.fintech.lms.common.constant.LoanTransactionType;
import com.smartosc.fintech.lms.dto.PaymentResultDto;
import com.smartosc.fintech.lms.dto.RepaymentRequestDto;
import com.smartosc.fintech.lms.dto.RepaymentResponseDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.LoanTransactionRepository;
import com.smartosc.fintech.lms.service.PaymentService;
import com.smartosc.fintech.lms.service.RepaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.RepaymentDto;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import com.smartosc.fintech.lms.repository.RepaymentRepository;
import com.smartosc.fintech.lms.service.mapper.RepaymentMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RepaymentServiceImpl implements RepaymentService {

    private LoanApplicationRepository loanApplicationRepository;
    private LoanTransactionRepository loanTransactionRepository;
    private PaymentService paymentGatewayService;
    private RepaymentRepository repaymentRepository;

    @Override
    public RepaymentResponseDto repayLoan(RepaymentRequestDto repaymentRequestDto) {
        Optional<LoanApplicationEntity>  existedLoanApplication = loanApplicationRepository.findLoanApplicationEntityByUuid(
                repaymentRequestDto.getUuid());
        LoanApplicationEntity loanApplicationEntity = existedLoanApplication.orElseThrow(EntityNotFoundException::new);
        LoanTransactionEntity loanTransactionEntity = null;
        RepaymentResponseDto repaymentResponseDto = new RepaymentResponseDto();
        PaymentResultDto paymentResultDto = paymentGatewayService.processRepayLoan(repaymentRequestDto);
        if(paymentResultDto.isSuccessful()){
            closeLoanApplication(loanApplicationEntity);
            loanTransactionEntity = saveRepaymentLoanTransaction(repaymentRequestDto, loanApplicationEntity);
        }
        repaymentResponseDto.setLoanTransactionEntity(loanTransactionEntity);
        return repaymentResponseDto;
    }

    private void closeLoanApplication(LoanApplicationEntity loanApplicationEntity){
        loanApplicationEntity.setStatus(LoanApplicationStatus.CLOSE.getValue());
        loanApplicationRepository.save(loanApplicationEntity);
    }

    private LoanTransactionEntity saveRepaymentLoanTransaction(RepaymentRequestDto repaymentRequestDto,
                                              LoanApplicationEntity loanApplicationEntity){
        LoanTransactionEntity loanTransactionEntity = createNewRepaymentLoanTransaction(
                repaymentRequestDto, loanApplicationEntity);
        loanTransactionRepository.save(loanTransactionEntity);
        return loanTransactionEntity;
    }

    private LoanTransactionEntity createNewRepaymentLoanTransaction(RepaymentRequestDto repaymentRequestDto,
                                                                    LoanApplicationEntity loanApplicationEntity){
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
        RepaymentEntity repaymentEntity = new RepaymentEntity();
        repaymentEntity.setUuid(loanApplicationEntity.getUuid());
        repaymentEntity.setInterestDue(loanApplicationEntity.getInterestDue());
        repaymentEntity.setPenaltyDue(loanApplicationEntity.getPenaltyDue());
        repaymentEntity.setPrincipalDue(loanApplicationEntity.getPrincipalDue());
        repaymentEntity.setFeeDue(loanApplicationEntity.getFeeDue());
        repaymentEntity.setUser(loanApplicationEntity.getUser());
        repaymentEntity.setLoanApplication(loanApplicationEntity);
        return Collections.singletonList(repaymentEntity);
    }
}
