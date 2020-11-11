package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.PersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonalInformationMapper {

    PersonalInformationMapper INSTANCE = Mappers.getMapper(PersonalInformationMapper.class);

    LoanPersonalInformationEntity mapToEntity(PersonalInformationDto personalInformationDto);

    PersonalInformationDto mapToDto(LoanPersonalInformationEntity loanPersonalInformationEntity);

}
