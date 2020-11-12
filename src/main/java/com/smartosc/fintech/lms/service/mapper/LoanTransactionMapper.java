package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.LoanTransactionDto;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanTransactionMapper {

    LoanTransactionMapper INSTANCE = Mappers.getMapper(LoanTransactionMapper.class);

    LoanTransactionEntity mapToEntity(LoanTransactionDto loanTransactionDto);

    LoanTransactionDto mapToDto(LoanTransactionEntity loanTransactionEntity);
}
