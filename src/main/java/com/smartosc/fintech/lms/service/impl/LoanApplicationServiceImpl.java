package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.service.LoanApplicationService;
import com.smartosc.fintech.lms.service.mapper.LoanApplicationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    @Override
    public LoanApplicationDto findLoanApplicationEntityByUuid(String uuid) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(
                () -> new EntityNotFoundException("Not found loan application with uuid : " + uuid));
        return LoanApplicationMapper.INSTANCE.mapToDto(loanApplicationEntity);
    }

    @Override
    public List<LoanApplicationDto> findLoanApplicationByUser(long id) {
        List<LoanApplicationDto> loanApplicationDtos = new ArrayList<>();
        List<LoanApplicationEntity> loanApplicationEntities = loanApplicationRepository.findLoanApplicationEntityByUserId(id);
        for (LoanApplicationEntity loanApplication : loanApplicationEntities) {
            loanApplicationDtos.add(LoanApplicationMapper.INSTANCE.mapToDto(loanApplication));
        }
        return loanApplicationDtos;
    }

}
