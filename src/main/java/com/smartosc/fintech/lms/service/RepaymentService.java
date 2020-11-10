package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.RepaymentDto;

import java.util.List;

public interface RepaymentService {

  /**
   * Create list of repayment form a loan application.
   *
   * @param loanApplicationDto a loan application
   * @return List<RepaymentDto> list of repayment
   */
  List<RepaymentDto> create(LoanApplicationDto loanApplicationDto);
}
