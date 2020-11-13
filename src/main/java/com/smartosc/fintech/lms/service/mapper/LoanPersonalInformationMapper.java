package com.smartosc.fintech.lms.service.mapper;


import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface LoanPersonalInformationMapper {

    LoanPersonalInformationMapper INSTANCE = Mappers.getMapper(LoanPersonalInformationMapper.class);
    LoanPersonalInformationDto mapToDto(LoanPersonalInformationEntity loanPersonalInformationEntity);

    @Named("mapToListPersonalInformationDto")
    Collection<LoanPersonalInformationDto> mapToListPersonalDto(Collection<LoanPersonalInformationEntity> loanPersonalInformationEntity);

}
