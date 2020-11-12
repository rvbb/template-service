package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.LoanApplicationDto;

import java.util.List;

public interface LoanApplicationService {

    LoanApplicationDto findLoanApplicationEntityByUuid(String uuid);

    List<LoanApplicationDto> findLoanApplicationByUser(long id);
}
