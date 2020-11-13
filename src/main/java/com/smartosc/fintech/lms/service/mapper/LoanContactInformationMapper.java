package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.LoanContactInformationDto;
import com.smartosc.fintech.lms.entity.LoanContactInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface LoanContactInformationMapper {

    LoanContactInformationMapper INSTANCE = Mappers.getMapper(LoanContactInformationMapper.class);

    LoanContactInformationDto mapToDto(LoanContactInformationEntity loanContactInformationEntity);

    @Named("mapListContactInformationToDto")
    Collection<LoanContactInformationDto> mapToListContractDto(Collection<LoanContactInformationEntity> listContactInformation);

}
