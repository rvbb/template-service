package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.dto.PersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import com.smartosc.fintech.lms.repository.PersonalInformationRepository;
import com.smartosc.fintech.lms.service.PersonalInformationService;
import com.smartosc.fintech.lms.service.mapper.PersonalInformationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class PersonalInformationServiceImpl implements PersonalInformationService {

    private PersonalInformationRepository personalInformationRepository;


    @Override
    public PersonalInformationDto
    updateLoanPersonalInformation(long id, PersonalInformationDto personalInformationDto) {
        if (!personalInformationRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        LoanPersonalInformationEntity loanPersonalInformationEntity = personalInformationRepository.findById(id).get();
        loanPersonalInformationEntity.setAddress(personalInformationDto.getAddress());
        loanPersonalInformationEntity.setEmailAddress(personalInformationDto.getEmailAddress());
//        loanPersonalInformationEntity.setId(id);
        personalInformationRepository.save(loanPersonalInformationEntity);
        return PersonalInformationMapper.INSTANCE.mapToDto(loanPersonalInformationEntity);
    }

}
