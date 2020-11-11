package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.PaymentRequest;
import com.smartosc.fintech.lms.dto.PaymentResultDto;
import com.smartosc.fintech.lms.dto.RepaymentRequestDto;

public interface PaymentService {
    void makePayment(PaymentRequest paymentRequest);
    PaymentResultDto processRepayLoan(RepaymentRequestDto repaymentRequestDto);
}
