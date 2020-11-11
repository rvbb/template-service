package com.smartosc.fintech.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author minhnd3@smartosc.com
 * @since 10-Nov-20
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentRequestDto implements Serializable {
    private String uuid;
    private BigDecimal totalMoney;
    private BigDecimal principalAmount;
    private BigDecimal interestAmount;
}
