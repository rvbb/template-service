package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.dto.PersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.PersonalInformationRepository;
import com.smartosc.fintech.lms.service.PersonalInformationService;
import com.smartosc.fintech.lms.service.mapper.PersonalInformationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonalInformationServiceImpl implements PersonalInformationService {

    private PersonalInformationRepository personalInformationRepository;
    private LoanApplicationRepository loanApplicationRepository;

    @Override
    public List<PersonalInformationDto>
    updateLoanPersonalInformation(long id, PersonalInformationDto personalInformationDto) {
        if (!loanApplicationRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        List<LoanPersonalInformationEntity> personalInformations = personalInformationRepository.getPersonalInformationByLoanApplicationId(id);
        List<PersonalInformationDto> personalInformationDtos = new ArrayList<>();
        for (LoanPersonalInformationEntity personalInformation : personalInformations) {
            personalInformation.setAddress(personalInformationDto.getAddress());
            personalInformation.setEmailAddress(personalInformationDto.getEmailAddress());
            personalInformationRepository.save(personalInformation);
            personalInformationDtos.add(PersonalInformationMapper.INSTANCE.mapToDto(personalInformation));
        }
        return personalInformationDtos;
    }

}
