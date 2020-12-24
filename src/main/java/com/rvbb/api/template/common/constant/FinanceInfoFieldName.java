package com.rvbb.api.template.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinanceInfoFieldName {
    STATUS,
    ID,
    COMPANY_NAME,
    COMPANY_ADDRESS,
    UUID,
    LAST_UPDATE,
    PRE_TAX_INCOME,
    EXPENSE;

    public static String asString() {
        return STATUS.toString()
                + ", " + LAST_UPDATE.toString()
                + ", " + COMPANY_NAME.toString();
    }

    public static boolean contains(String enumAsString){
        for(FinanceInfoFieldName item : FinanceInfoFieldName.values()){
            return item.toString().equalsIgnoreCase(enumAsString);
        }
        return false;
    }
}
