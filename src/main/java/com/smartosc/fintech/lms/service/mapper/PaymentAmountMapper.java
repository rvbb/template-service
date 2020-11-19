package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.common.util.DateTimeUtil;
import com.smartosc.fintech.lms.dto.PaymentAmountDto;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Collection;

@Mapper
public interface PaymentAmountMapper {

  PaymentAmountMapper INSTANCE = Mappers.getMapper(PaymentAmountMapper.class);

  @Mappings({
          @Mapping(source = "principalDue",target = "principal"),
          @Mapping(source = "dueDate",target = "dueDate",qualifiedByName = "mapDuedateToString")
  })
  PaymentAmountDto entityToDto(RepaymentEntity repaymentEntity);


  @Named("mapToListPaymentAmount")
  Collection<PaymentAmountDto> mapToListPaymentAmount(Collection<RepaymentEntity> repaymentEntities);

  @Named("mapDuedateToString")
  static String mapBirthdayToString(Timestamp date) throws ParseException {
      return DateTimeUtil.getFormatTimestamp(date);
  }
}
