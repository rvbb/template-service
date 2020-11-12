package com.smartosc.fintech.lms.service.mapper;


import com.smartosc.fintech.lms.dto.LoanKycInformationDto;
import com.smartosc.fintech.lms.entity.LoanKycInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface LoanKycInformationMapper {

    LoanKycInformationMapper INSTANCE = Mappers.getMapper(LoanKycInformationMapper.class);
    LoanKycInformationDto mapToDto(LoanKycInformationEntity loanKycInformationEntity);
    @Named("mapToListLoanKycInformationDto")
    Collection<LoanKycInformationDto> mapToListKycDto(Collection<LoanKycInformationEntity> loanKycInformationEntity);


}
