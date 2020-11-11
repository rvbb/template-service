package com.smartosc.fintech.lms.dto;

import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepaymentResponseDto implements Serializable {
    private LoanTransactionEntity loanTransactionEntity;
}
