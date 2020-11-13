package com.smartosc.fintech.lms.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoanApplicationStatus {
    DROP_OFF(0),
    APPROVED(1),
    REJECT(2),
    SIGN(3),
    ACTIVE(4),
    CLOSE(5);

    private int value;
}
