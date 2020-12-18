package com.rvbb.api.template.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BankAccountType {
    TYPE_LENDER(1),
    TYPE_BORROWER(2);
    private Integer value;
}
