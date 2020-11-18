package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.PaymentAmountDto;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

@Mapper
public interface PaymentAmountMapper {

  PaymentAmountMapper INSTANCE = Mappers.getMapper(PaymentAmountMapper.class);

  @Mappings({
          @Mapping(source = "principalPaid",target = "principal"),
          @Mapping(source = "dueDate",target = "dueDate",qualifiedByName = "mapDuedateToString")
  })
  PaymentAmountDto entityToDto(RepaymentEntity repaymentEntity);


  @Named("mapToListPaymentAmount")
  Collection<PaymentAmountDto> mapToListPaymentAmount(Collection<RepaymentEntity> repaymentEntities);

  @Named("mapDuedateToString")
  static String mapBirthdayToString(Timestamp date) throws ParseException {
    if (date != null) {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
      return format.format(date);
    }
    return "";
  }
}
