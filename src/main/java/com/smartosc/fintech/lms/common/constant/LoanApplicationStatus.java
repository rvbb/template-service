package com.smartosc.fintech.lms.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author minhnd3@smartosc.com
 * @since 11-Nov-20
 */
@Getter
@AllArgsConstructor
public enum LoanApplicationStatus {
    APPROVED(1),
    CLOSED(2),
    REJECTED(0);
    private int value;
}
