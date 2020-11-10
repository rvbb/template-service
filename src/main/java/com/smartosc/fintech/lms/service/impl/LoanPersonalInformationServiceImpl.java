package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import com.smartosc.fintech.lms.repository.LoanPersonalInformationRepository;
import com.smartosc.fintech.lms.service.LoanPersonalInformationService;
import com.smartosc.fintech.lms.service.mapper.LoanPersonalInformationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class LoanPersonalInformationServiceImpl implements LoanPersonalInformationService {

    private LoanPersonalInformationRepository loanPersonalInformationRepository;


    @Override
    public LoanPersonalInformationDto updateLoanPersonalInformation(long id, LoanPersonalInformationDto loanPersonalInformationDto) {
        if (!loanPersonalInformationRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        LoanPersonalInformationEntity loanPersonalInformationEntity = loanPersonalInformationRepository.findById(id).get();
        loanPersonalInformationEntity.setAddress(loanPersonalInformationDto.getAddress());
        loanPersonalInformationEntity.setEmailAddress(loanPersonalInformationDto.getEmailAddress());
//        loanPersonalInformationEntity.setId(id);
        loanPersonalInformationRepository.save(loanPersonalInformationEntity);
        return LoanPersonalInformationMapper.INSTANCE.mapToDto(loanPersonalInformationEntity);
    }

}
