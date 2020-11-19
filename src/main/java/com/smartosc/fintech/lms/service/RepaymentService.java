package com.smartosc.fintech.lms.service;


import com.smartosc.fintech.lms.dto.PaymentResponse;
import com.smartosc.fintech.lms.dto.RepaymentRequestDto;
import com.smartosc.fintech.lms.dto.RepaymentResponseDto;
import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.RepaymentDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.RepaymentEntity;

import java.math.BigDecimal;
import java.util.List;

public interface RepaymentService {

    RepaymentResponseDto payBack(RepaymentRequestDto repaymentRequestDto);

    /**
     * Generate repayments form a loan application.
     *
     * @param loanApplicationDto a loan application
     * @return List<RepaymentDto> list of repayment
     */
    List<RepaymentDto> calculate(LoanApplicationDto loanApplicationDto);

    /**
     * Generate repayments form a loan application by loanApplicationId.
     *
     * @param loanApplicationUuid loan application uuid
     * @return List<RepaymentDto> list of repayment
     */
    List<RepaymentDto> calculate(String loanApplicationUuid);

    /**
     * Calculate Accrued Interest
     *
     * @param loanApplicationEntity loan application
     * @return BigDecimal
     */
    BigDecimal calculateAccruedInterest(LoanApplicationEntity loanApplicationEntity);

    RepaymentDto get(String id);

    void calculateAndSaveRepayment(RepaymentRequestDto repaymentRequestDto, RepaymentEntity repaymentEntity);

    RepaymentResponseDto processPayResult(PaymentResponse paymentResponse);
}
