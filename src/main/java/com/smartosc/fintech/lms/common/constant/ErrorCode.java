package com.smartosc.fintech.lms.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCode {
    // Define business exception code from 1000
    public static final int PAYMENT_GATEWAY_FAIL = 1001;
    public static final int PAYMENT_GATEWAY_TIMEOUT = 1002;
}
