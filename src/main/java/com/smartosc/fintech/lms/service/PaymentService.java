package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.PaymentRequest;
import com.smartosc.fintech.lms.dto.PaymentResultDto;
import com.smartosc.fintech.lms.dto.RepayRequestInPaymentServiceDto;

public interface PaymentService {
    void processFunding(PaymentRequest paymentRequest);

    PaymentResultDto processRepayLoan(RepayRequestInPaymentServiceDto repaymentRequestDto);
}
