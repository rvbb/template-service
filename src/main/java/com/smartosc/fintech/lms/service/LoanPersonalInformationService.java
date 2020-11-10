package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;

public interface LoanPersonalInformationService {

    LoanPersonalInformationDto updateLoanPersonalInformation(long id,LoanPersonalInformationDto loanPersonalInformation);
}
