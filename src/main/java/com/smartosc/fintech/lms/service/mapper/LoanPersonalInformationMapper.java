package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanPersonalInformationMapper {

    LoanPersonalInformationMapper INSTANCE = Mappers.getMapper(LoanPersonalInformationMapper.class);

    LoanPersonalInformationEntity mapToEntity(LoanPersonalInformationDto loanPersonalInformationDto);

    LoanPersonalInformationDto mapToDto(LoanPersonalInformationEntity loanPersonalInformationEntity);

}
