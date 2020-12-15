package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.RepaymentDto;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RepaymentMapper {

  RepaymentMapper INSTANCE = Mappers.getMapper(RepaymentMapper.class);

  RepaymentDto entityToDto(RepaymentEntity repaymentEntity);

  RepaymentEntity dtoToEntity(RepaymentDto repaymentDto);

  @Named("mapToListRepaymentDto")
  Collection<RepaymentDto> mapToListRepaymentDto(Collection<RepaymentEntity> repaymentDto);

}
