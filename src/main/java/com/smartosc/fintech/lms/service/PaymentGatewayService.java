package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.PaymentResultDto;
import com.smartosc.fintech.lms.dto.RepaymentRequestDto;

public interface PaymentGatewayService {
    public PaymentResultDto processRepayLoan(RepaymentRequestDto repaymentRequestDto);
}
