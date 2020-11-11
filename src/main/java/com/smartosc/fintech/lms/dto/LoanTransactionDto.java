package com.smartosc.fintech.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanTransactionDto implements Serializable {
    private long id;

    private String uuid;

    private BigDecimal amount;

    private BigDecimal balance;

    private Timestamp creationDate;

    private Timestamp entryDate;

    private BigDecimal feesAmount;

    private BigDecimal interestAmount;

    private BigDecimal interestRate;

    private BigDecimal penaltyAmount;

    private BigDecimal principalAmount;

    private BigDecimal principalBalance;

    private BigDecimal taxOnFeesAmount;

    private BigDecimal taxOnInterestAmount;

    private BigDecimal taxOnPenaltyAmount;

    private String type;
}
