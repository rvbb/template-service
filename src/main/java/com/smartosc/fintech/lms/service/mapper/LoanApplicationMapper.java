package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper(uses = {LoanContactInformationMapper.class,
        LoanJobInformationMapper.class,
        LoanPersonalInformationMapper.class,
        PaymentAmountMapper.class,
        BigDecimalMapper.class
},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanApplicationMapper {

    LoanApplicationMapper INSTANCE = Mappers.getMapper(LoanApplicationMapper.class);

    @Mapping(source = "contractNumber", target = "accountNumber")
    @Mapping(source = "loanAmount", target = "loanAmount", qualifiedByName = "mapToBigDecimalScale")
    @Mapping(source = "status", target = "loanStatus")
    @Mapping(source = "interestRate", target = "interestRate", qualifiedByName = "mapStringToBigDecimalScale")
    @Mapping(source = "loanContactInformation", target = "loanContactInformation",
            qualifiedByName = "mapListContactInformationToDto")
    @Mapping(source = "loanJobInformation", target = "loanJobInformation",
            qualifiedByName = "mapToListJobInformationDto")
    LoanApplicationDto mapToDto(LoanApplicationEntity loanApplicationEntity);

    @Named("mapInterestRateToInteger")
    static BigDecimal mapInterestRateToInteger(String interestRate) {
        return new BigDecimal(interestRate);
    }
}
