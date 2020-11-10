package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.PersonalInformationDto;

import java.util.List;

public interface PersonalInformationService {

    List<PersonalInformationDto> updateLoanPersonalInformation(long id, PersonalInformationDto loanPersonalInformation);
}
