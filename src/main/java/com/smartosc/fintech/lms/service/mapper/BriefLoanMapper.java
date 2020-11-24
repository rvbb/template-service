package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.BriefLoanDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BriefLoanMapper {

    BriefLoanMapper INSTANCE = Mappers.getMapper(BriefLoanMapper.class);

    @Mappings({
            @Mapping(source = "contractNumber", target = "accountNumber"),
            @Mapping(source = "loanProduct.name", target = "loanType"),
    })
    BriefLoanDto mapToDto(LoanApplicationEntity loanApplicationEntity);

    List<BriefLoanDto> mapToListBriefLoanDto(List<LoanApplicationEntity> loanApplicationEntities);

}
