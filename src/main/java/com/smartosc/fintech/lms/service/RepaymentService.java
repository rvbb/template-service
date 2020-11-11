package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.RepaymentDto;

import java.util.List;

public interface RepaymentService {

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
   * @param loanApplicationUuid loan application id
   * @return List<RepaymentDto> list of repayment
   */
  List<RepaymentDto> calculate(String loanApplicationUuid);
}