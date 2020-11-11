package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.PersonalInformationDto;

import java.util.List;

public interface PersonalInformationService {

    PersonalInformationDto getLoanPersonalInformation(String uuid);

    PersonalInformationDto updateLoanPersonalInformation(long id, PersonalInformationDto loanPersonalInformation);

    List<PersonalInformationDto>
    updateLoanPersonalInformation(String id, PersonalInformationDto loanPersonalInformation);
}
