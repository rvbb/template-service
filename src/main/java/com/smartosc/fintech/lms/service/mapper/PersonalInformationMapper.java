package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.InputPersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonalInformationMapper {

    PersonalInformationMapper INSTANCE = Mappers.getMapper(PersonalInformationMapper.class);

    LoanPersonalInformationEntity mapToEntity(InputPersonalInformationDto inputPersonalInformationDto);

    InputPersonalInformationDto mapToDto(LoanPersonalInformationEntity personalInformationDto);

    InputPersonalInformationDto mapToListDto(LoanPersonalInformationEntity loanPersonalInformationEntity);

}
