package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.dto.InputPersonalInformationDto;
import com.smartosc.fintech.lms.dto.LoanJobInformationDto;
import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.dto.PersonalFinInfoRequest;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanJobInformationEntity;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import com.smartosc.fintech.lms.repository.IPersonalFinInfoRepository;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.PersonalInformationRepository;
import com.smartosc.fintech.lms.service.PersonalInformationService;
import com.smartosc.fintech.lms.service.mapper.LoanJobInformationMapper;
import com.smartosc.fintech.lms.service.mapper.LoanPersonalInformationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

/**
 * Manage personal information and financial information
 **/
@Service
@AllArgsConstructor
public class PersonalInformationServiceImpl implements PersonalInformationService {

    private final PersonalInformationRepository personalInformationRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final IPersonalFinInfoRepository personalFinInfoRepository;

    /**
     * get personal information by loan application with uuid
     *
     * @param uuid created by tuanhv2
     */
    @Override
    @SMFLogger
    public LoanPersonalInformationDto getLoanPersonalInformation(String uuid) {
        LoanPersonalInformationEntity loanPersonalInformation = personalInformationRepository.findByLoanApplicationUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Not found personal information by loan application with uuid : " + uuid));
        return LoanPersonalInformationMapper.INSTANCE.mapToDto(loanPersonalInformation);
    }

    @Override
    @SMFLogger
    public List<LoanPersonalInformationDto> updateLoanPersonalInformation(String uuid, InputPersonalInformationDto inputPersonalInformationDto) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(
                () -> new EntityNotFoundException("Not found loan application with uuid : " + uuid));

        Collection<LoanPersonalInformationEntity> personalInformations = loanApplicationEntity.getLoanPersonalInformation();
        List<LoanPersonalInformationDto> loanPersonalInformationDtos = new ArrayList<>();
        for (LoanPersonalInformationEntity personalInformation : personalInformations) {
            personalInformation.setAddress(inputPersonalInformationDto.getAddress());
            personalInformation.setEmailAddress(inputPersonalInformationDto.getEmailAddress());
            personalInformationRepository.save(personalInformation);
            loanPersonalInformationDtos.add(LoanPersonalInformationMapper.INSTANCE.mapToDto(personalInformation));
        }
        return loanPersonalInformationDtos;
    }

    @SMFLogger
    public LoanJobInformationDto getPersonalFinInfo(String uuid) {
        LoanJobInformationEntity finInfoEntity = personalFinInfoRepository.findByLoanApplicationUuid(uuid)//equals findByLoanApplication_Uuid
                .orElseThrow(() -> new EntityNotFoundException("Not found personal financial information by loan application uuid : " + uuid));
        return LoanJobInformationMapper.INSTANCE.toDto(finInfoEntity);
    }

    @SMFLogger
    public List<LoanJobInformationDto> updatePersonalFinInfo(String uuid, PersonalFinInfoRequest request) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(
                () -> new EntityNotFoundException("Not found loan application with uuid : " + uuid));
        Collection<LoanJobInformationEntity> finInfoList = loanApplicationEntity.getLoanJobInformation();
        List<LoanJobInformationDto> result = new ArrayList<LoanJobInformationDto>();
        for (LoanJobInformationEntity finInfo : finInfoList) {
            LoanJobInformationEntity entity = LoanJobInformationMapper.INSTANCE.toEntity(request);
            entity.setId(finInfo.getId());
            entity.setLastUpdate(new Date());
            personalFinInfoRepository.save(entity);
            result.add(LoanJobInformationMapper.INSTANCE.toDto(entity));
        }
        return result;
    }
}
