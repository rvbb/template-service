package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.dto.PersonalInformationDto;

import java.util.List;

public interface PersonalInformationService {

    LoanPersonalInformationDto getLoanPersonalInformation(String uuid);

    List<PersonalInformationDto>
    updateLoanPersonalInformation(String id, PersonalInformationDto loanPersonalInformation);
}
