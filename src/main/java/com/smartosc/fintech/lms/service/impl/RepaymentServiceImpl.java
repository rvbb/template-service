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
import com.smartosc.fintech.lms.service.PaymentGatewayService;
import com.smartosc.fintech.lms.service.RepaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author minhnd3@smartosc.com
 * @since 10-Nov-20
 */
@Service
@AllArgsConstructor
public class RepaymentServiceImpl implements RepaymentService {

    private LoanApplicationRepository loanApplicationRepository;
    private LoanTransactionRepository loanTransactionRepository;
    private PaymentGatewayService paymentGatewayService;

    @Override
    public RepaymentResponseDto repayLoan(RepaymentRequestDto repaymentRequestDto) {
        LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findLoanApplicationEntityByUuid(
                repaymentRequestDto.getUuid());
        if(loanApplicationEntity == null){
            throw new EntityNotFoundException();
        }
        LoanTransactionEntity loanTransactionEntity = null;

        RepaymentResponseDto repaymentResponseDto = new RepaymentResponseDto();

        PaymentResultDto paymentResultDto = paymentGatewayService.processRepayLoan(repaymentRequestDto);
        if(paymentResultDto.isSuccessful()){
            closeLoanApplicationStatus(loanApplicationEntity);
            loanTransactionEntity = saveRepaymentLoanTransaction(repaymentRequestDto, loanApplicationEntity);
        }
         repaymentResponseDto.setLoanTransactionEntity(loanTransactionEntity);
        return repaymentResponseDto;
    }

    private void closeLoanApplicationStatus(LoanApplicationEntity loanApplicationEntity){
        loanApplicationEntity.setStatus(LoanApplicationStatus.CLOSED.getValue());
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
        loanTransactionEntity.setType(LoanTransactionType.REPAYMENT.getType());
        loanTransactionEntity.setUser(loanApplicationEntity.getUser());
        loanTransactionEntity.setLoanApplication(loanApplicationEntity);

        return loanTransactionEntity;
    }
}
