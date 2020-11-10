package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.RepaymentRequestDto;
import com.smartosc.fintech.lms.dto.RepaymentResponseDto;

/**
 * @author minhnd3@smartosc.com
 * @since 10-Nov-20
 */
public interface RepaymentService {
    public RepaymentResponseDto repayLoan(RepaymentRequestDto repaymentRequestDto);
}
