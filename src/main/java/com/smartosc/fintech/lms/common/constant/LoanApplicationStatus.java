package com.smartosc.fintech.lms.common.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum LoanApplicationStatus {
    APPROVED (1),
    FUNDED (2),
    PASS_DUE(3),
    CLOSE(4);
    private int value;
    LoanApplicationStatus(int value) {
        this.value = value;
    }

}
