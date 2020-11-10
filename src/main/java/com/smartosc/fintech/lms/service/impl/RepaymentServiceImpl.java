package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.RepaymentDto;
import com.smartosc.fintech.lms.service.RepaymentService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RepaymentServiceImpl implements RepaymentService {

  @Override
  public List<RepaymentDto> create(LoanApplicationDto loanApplicationDto) {
    return Collections.emptyList();
  }
}
