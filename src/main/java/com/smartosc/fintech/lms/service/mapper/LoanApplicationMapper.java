package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanApplicationMapper {

    LoanApplicationMapper INSTANCE = Mappers.getMapper(LoanApplicationMapper.class);

    LoanApplicationDto mapToDto(LoanApplicationEntity loanApplicationEntity);
}
