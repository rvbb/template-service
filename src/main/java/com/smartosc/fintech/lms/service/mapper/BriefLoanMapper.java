package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.BriefLoanDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface BriefLoanMapper {

    BriefLoanMapper INSTANCE = Mappers.getMapper(BriefLoanMapper.class);

    static BriefLoanDto mapToDto(LoanApplicationEntity loanApplicationEntity) {
        BriefLoanDto briefLoanDto = new BriefLoanDto();
        briefLoanDto.setUuid(loanApplicationEntity.getUuid());
        briefLoanDto.setAccountNumber(loanApplicationEntity.getContractNumber());
        briefLoanDto.setStatus(loanApplicationEntity.getStatus());

        BigDecimal outstandingBalance = loanApplicationEntity.getLoanAmount();
        if (loanApplicationEntity.getPrincipalPaid() != null)
            outstandingBalance = outstandingBalance.subtract(loanApplicationEntity.getPrincipalPaid());
        briefLoanDto.setOutstandingBalance(outstandingBalance);
        briefLoanDto.setLoanType(loanApplicationEntity.getLoanProduct().getName());
        return briefLoanDto;
    };

    List<BriefLoanDto> mapToListBriefLoanDto(List<LoanApplicationEntity> loanApplicationEntities);

}
