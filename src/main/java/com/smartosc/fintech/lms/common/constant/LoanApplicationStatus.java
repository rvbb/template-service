package com.smartosc.fintech.lms.common.constant;


import lombok.Getter;

@Getter
public enum LoanApplicationStatus {
    SIGN(1),
    ACTIVE(2),
    CLOSE(3);
    private int value;
    LoanApplicationStatus(int value) {
        this.value = value;
    }

}
