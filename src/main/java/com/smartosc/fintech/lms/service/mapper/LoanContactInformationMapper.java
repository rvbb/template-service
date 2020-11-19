package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.common.util.DateTimeUtil;
import com.smartosc.fintech.lms.dto.LoanContactInformationDto;
import com.smartosc.fintech.lms.entity.LoanContactInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Date;
import java.text.ParseException;
import java.util.Collection;

@Mapper(uses = {LoanContactInformationMapper.class})
public interface LoanContactInformationMapper {

    LoanContactInformationMapper INSTANCE = Mappers.getMapper(LoanContactInformationMapper.class);

    @Mappings({
            @Mapping(source = "refDob",target = "refDob",qualifiedByName = "mapBirthdayToString")
    })
    LoanContactInformationDto mapToDto(LoanContactInformationEntity loanContactInformationEntity);

    @Named("mapListContactInformationToDto")
    Collection<LoanContactInformationDto> mapToListContractDto(Collection<LoanContactInformationEntity> listContactInformation);

    @Named("mapBirthdayToString")
    static String mapBirthdayToString(Date date) throws ParseException {
        if (date != null) {
           return DateTimeUtil.formatDate(date);
        }
        return "";
    }

}
