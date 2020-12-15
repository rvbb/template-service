package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.LoanTransactionDto;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanTransactionMapper {

    LoanTransactionMapper INSTANCE = Mappers.getMapper(LoanTransactionMapper.class);

    LoanTransactionEntity mapToEntity(LoanTransactionDto loanTransactionDto);

    LoanTransactionDto mapToDto(LoanTransactionEntity loanTransactionEntity);

    @Named("mapToListLoanTransactionDto")
    Collection<LoanTransactionDto> mapToListTransactionDto(Collection<LoanTransactionEntity> loanTransactionEntity);

}
