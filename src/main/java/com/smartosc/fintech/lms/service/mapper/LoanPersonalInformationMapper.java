package com.smartosc.fintech.lms.service.mapper;


import com.smartosc.fintech.lms.common.util.DateTimeUtil;
import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Date;
import java.text.ParseException;
import java.util.Collection;

@Mapper(uses = {LoanPersonalInformationMapper.class})
public interface LoanPersonalInformationMapper {

    LoanPersonalInformationMapper INSTANCE = Mappers.getMapper(LoanPersonalInformationMapper.class);

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", qualifiedByName = "mapBirthdayToString")
    LoanPersonalInformationDto mapToDto(LoanPersonalInformationEntity loanPersonalInformationEntity);

    @Named("mapToListPersonalInformationDto")
    Collection<LoanPersonalInformationDto> mapToListPersonalDto(Collection<LoanPersonalInformationEntity> loanPersonalInformationEntity);

    @Named("mapBirthdayToString")
    static String mapBirthdayToString(Date date) throws ParseException {
        if (date != null) {
           return DateTimeUtil.formatDate(date);
        }
        return "";
    }

}
