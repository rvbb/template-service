package com.smartosc.fintech.lms.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanTransactionRequest {
    String uuid;
    long fromDate;
    long toDate;
    int page;
    int size;
}
