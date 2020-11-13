package com.smartosc.fintech.lms.service;


import com.smartosc.fintech.lms.dto.RepaymentRequestDto;
import com.smartosc.fintech.lms.dto.RepaymentResponseDto;
import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.RepaymentDto;
import com.smartosc.fintech.lms.entity.RepaymentEntity;

import java.util.List;

public interface RepaymentService {

    RepaymentResponseDto repayLoan(RepaymentRequestDto repaymentRequestDto);

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

    RepaymentDto get(String id);

    void calculateAndSaveRepayment(RepaymentRequestDto repaymentRequestDto, RepaymentEntity repaymentEntity);
}
