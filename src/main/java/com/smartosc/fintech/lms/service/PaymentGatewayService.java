package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.PaymentResultDto;
import com.smartosc.fintech.lms.dto.RepaymentRequestDto;

/**
 * @author minhnd3@smartosc.com
 * @since 11-Nov-20
 */
public interface PaymentGatewayService {
    public PaymentResultDto processRepayLoan(RepaymentRequestDto repaymentRequestDto);
}
