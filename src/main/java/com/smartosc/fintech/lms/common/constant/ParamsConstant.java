package com.smartosc.fintech.lms.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParamsConstant {
    public static final int LEAD_DAY = 3;
    public static final BigDecimal DAY_OF_YEAR = BigDecimal.valueOf(365);
}
