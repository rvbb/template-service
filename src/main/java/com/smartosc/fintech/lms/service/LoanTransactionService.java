package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.LoanTransactionDto;

public interface LoanTransactionService {
    LoanTransactionDto getLoanTransaction(String uuid);
}
