package com.rvbb.api.template.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FinanceInfoRes {
    private long id;

    private BigDecimal preTaxIncome;

    private String companyName;

    private String companyAddress;

    private String latestUpdate;

    private BigDecimal expense;
}
