package com.smartosc.fintech.lms.service.mapper;


import com.smartosc.fintech.lms.dto.LoanJobInformationDto;
import com.smartosc.fintech.lms.entity.LoanJobInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface LoanJobInformationMapper {

    LoanJobInformationMapper INSTANCE = Mappers.getMapper(LoanJobInformationMapper.class);
    LoanJobInformationDto mapToDto(LoanJobInformationEntity loanJobInformationEntity);
    @Named("mapToListJobInformationDto")
    Collection<LoanJobInformationDto> mapToListJobInformationDto(Collection<LoanJobInformationEntity> loanJobInformationEntity);

}
