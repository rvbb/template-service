package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.dto.PaymentResultDto;
import com.smartosc.fintech.lms.dto.RepaymentRequestDto;
import com.smartosc.fintech.lms.service.PaymentGatewayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class PaymentGatewayServiceImpl implements PaymentGatewayService {
    @Override
    public PaymentResultDto processRepayLoan(RepaymentRequestDto repaymentRequestDto) {
        return new PaymentResultDto();
    }
}
