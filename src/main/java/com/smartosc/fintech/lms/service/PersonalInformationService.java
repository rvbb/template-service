package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.PersonalInformationDto;

public interface PersonalInformationService {

    PersonalInformationDto getLoanPersonalInformation(String uuid);

    PersonalInformationDto updateLoanPersonalInformation(long id, PersonalInformationDto loanPersonalInformation);
}
