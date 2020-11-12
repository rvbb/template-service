package com.smartosc.fintech.lms.service.mapper;


import com.smartosc.fintech.lms.dto.LoanDisbursementMethodDto;
import com.smartosc.fintech.lms.entity.LoanDisbursementMethodEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface LoanDisburementMethodMapper {

    LoanDisburementMethodMapper INSTANCE = Mappers.getMapper(LoanDisburementMethodMapper.class);

    LoanDisbursementMethodDto mapToDto(LoanDisbursementMethodEntity loanDisbursementMethodEntity);

    @Named("mapToListDisbursementMethodDto")
    Collection<LoanDisbursementMethodDto> mapToListDisbursementDto(Collection<LoanDisbursementMethodEntity> loanDisbursementMethodEntity);

}
