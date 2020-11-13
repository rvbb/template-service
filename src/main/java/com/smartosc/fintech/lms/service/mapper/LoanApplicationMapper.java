package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {LoanContactInformationMapper.class, LoanCreditScoreMapper.class,
        LoanDisburementMethodMapper.class, LoanJobInformationMapper.class,
        LoanKycInformationMapper.class, LoanPersonalInformationMapper.class,
        LoanProductMapper.class, LoanTransactionMapper.class,
        RepaymentMapper.class, UserMapper.class
})
public interface LoanApplicationMapper {

    LoanApplicationMapper INSTANCE = Mappers.getMapper(LoanApplicationMapper.class);

    @Mappings({
            @Mapping(source = "user", target = "user",
                    qualifiedByName = "mapUserEntityToDto"),
            @Mapping(source = "loanProduct", target = "loanProduct",
                    qualifiedByName = "mapLoanProductToDto"),
            @Mapping(source = "loanContactInformation", target = "loanContactInformation",
                    qualifiedByName = "mapListContactInformationToDto"),
            @Mapping(source = "loanCreditScores", target = "loanCreditScores",
                    qualifiedByName = "mapToListLoanCreditScoreDto"),
            @Mapping(source = "loanDisbursementMethods", target = "loanDisbursementMethods",
                    qualifiedByName = "mapToListDisbursementMethodDto"),
            @Mapping(source = "loanJobInformation", target = "loanJobInformation",
                    qualifiedByName = "mapToListJobInformationDto"),
            @Mapping(source = "loanKycInformation", target = "loanKycInformation",
                    qualifiedByName = "mapToListLoanKycInformationDto"),
            @Mapping(source = "loanPersonalInformation", target = "loanPersonalInformation",
                    qualifiedByName = "mapToListPersonalInformationDto"),
            @Mapping(source = "loanTransactions", target = "loanTransactions",
                    qualifiedByName = "mapToListLoanTransactionDto"),
            @Mapping(source = "repayments", target = "repayments",
                    qualifiedByName = "mapToListRepaymentDto"),
    })
    LoanApplicationDto mapToDto(LoanApplicationEntity loanApplicationEntity);
}
