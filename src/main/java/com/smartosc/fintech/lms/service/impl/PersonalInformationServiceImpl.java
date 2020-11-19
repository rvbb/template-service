package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.dto.PersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.PersonalInformationRepository;
import com.smartosc.fintech.lms.service.PersonalInformationService;
import com.smartosc.fintech.lms.service.mapper.LoanPersonalInformationMapper;
import com.smartosc.fintech.lms.service.mapper.PersonalInformationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonalInformationServiceImpl implements PersonalInformationService {

    private final PersonalInformationRepository personalInformationRepository;
    private final LoanApplicationRepository loanApplicationRepository;


    /**
     * get personal information by loan application with uuid
     * @param uuid
     * created by tuanhv2
     */
    @Override
    @SMFLogger
    public LoanPersonalInformationDto getLoanPersonalInformation(String uuid) {
        LoanPersonalInformationEntity loanPersonalInformation = personalInformationRepository.findByLoanApplicationUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Not found personal information by loan application with uuid : " + uuid));
        return LoanPersonalInformationMapper.INSTANCE.mapToDto(loanPersonalInformation);
    }

    @Override
    public List<PersonalInformationDto> updateLoanPersonalInformation(String uuid, PersonalInformationDto personalInformationDto) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(
                () -> new EntityNotFoundException("Not found loan application with uuid : " + uuid));

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
