package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.dto.PersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.PersonalInformationRepository;
import com.smartosc.fintech.lms.service.PersonalInformationService;
import com.smartosc.fintech.lms.service.mapper.PersonalInformationMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonalInformationServiceImpl implements PersonalInformationService {

    @Autowired
    private PersonalInformationRepository personalInformationRepository;
    private LoanApplicationRepository loanApplicationRepository;

    /**
     * get personal information by loan application with uuid
     * @param uuid
     * created by tuanhv2
     */
    @Override
    @SMFLogger
    public PersonalInformationDto getLoanPersonalInformation(String uuid) {
        LoanPersonalInformationEntity loanPersonalInformation = personalInformationRepository.findByLoanApplicationUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Not found personal information by loan application with uuid : " + uuid));
        return PersonalInformationMapper.INSTANCE.mapToDto(loanPersonalInformation);
    }

    @Override
    public List<PersonalInformationDto>
    updateLoanPersonalInformation(String uuid, PersonalInformationDto personalInformationDto) {

        LoanApplicationEntity loanApplicationEntity =
                loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        if (loanApplicationEntity == null) {
            throw new EntityNotFoundException();
        }
        Collection<LoanPersonalInformationEntity> personalInformations = loanApplicationEntity.getLoanPersonalInformation();
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
