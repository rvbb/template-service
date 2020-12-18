package com.rvbb.api.template.service.mapper;


import com.rvbb.api.template.dto.FinanceInfoInput;
import com.rvbb.api.template.dto.FinanceInfoRes;
import com.rvbb.api.template.entity.FinanceInfoEntity;
import com.rvbb.api.template.common.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FinanceInfoMapper {

    FinanceInfoMapper INSTANCE = Mappers.getMapper(FinanceInfoMapper.class);

    @Mapping(source = "lastUpdate", target = "latestUpdate", qualifiedByName = "convertDateToString")
    @Mapping(source = "expense", target = "expense", qualifiedByName = "round")
    @Mapping(source = "preTaxIncome", target = "preTaxIncome", qualifiedByName = "round")
    FinanceInfoRes toDto(FinanceInfoEntity loanJobInformationEntity);

    @Named("mapToListJobInformationDto")
    Collection<FinanceInfoRes> mapToListJobInformationDto(Collection<FinanceInfoEntity> loanJobInformationEntity);

    FinanceInfoEntity toEntity(FinanceInfoInput request);

    @Named("convertDateToString")
    static String convertDateToString(Date date) {
        return DateTimeUtil.date2string(date, false);
    }

    @Named("round")
    static BigDecimal roundDouble(BigDecimal val) {
        return BigDecimal.valueOf(Math.floor(val.doubleValue() * 100) / 100);
    }

}

