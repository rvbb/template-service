package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.dto.PersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import com.smartosc.fintech.lms.repository.PersonalInformationRepository;
import com.smartosc.fintech.lms.service.PersonalInformationService;
import com.smartosc.fintech.lms.service.mapper.PersonalInformationMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
@Slf4j
public class PersonalInformationServiceImpl implements PersonalInformationService {

    @Autowired
    private PersonalInformationRepository personalInformationRepository;


    /**
     * get personal information by loan application with uuid
     * @param uuid
     * created by tuanhv2
     */
    @Override
    public PersonalInformationDto getLoanPersonalInformation(String uuid) {
        log.info("get personal information by loan application with uuid : {}", uuid);
        LoanPersonalInformationEntity loanPersonalInformation = personalInformationRepository.findPersonalInformationbyLoanAppliaction(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Not found personal information by loan application with uuid : " + uuid));
        return PersonalInformationMapper.INSTANCE.mapToDto(loanPersonalInformation);
    }

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
