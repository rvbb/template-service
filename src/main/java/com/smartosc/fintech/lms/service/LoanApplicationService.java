package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.LoanApplicationDto;

public interface LoanApplicationService {

    LoanApplicationDto findLoanApplicationEntityByUuid(String uuid);
}
