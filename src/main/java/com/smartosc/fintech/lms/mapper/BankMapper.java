package com.smartosc.fintech.lms.mapper;

import com.smartosc.fintech.lms.dto.BankDto;
import com.smartosc.fintech.lms.entity.BankEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BankMapper {

  BankMapper INSTANCE = Mappers.getMapper(BankMapper.class);

  BankDto bankEntityToBankDto(BankEntity bankEntity);

  BankEntity bankDtoToBankEntity(BankDto bankDto);
}
