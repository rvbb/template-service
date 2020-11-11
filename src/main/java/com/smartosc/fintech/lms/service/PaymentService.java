package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.PaymentRequest;

public interface PaymentService {
    void makePayment(PaymentRequest paymentRequest);
}
