package com.smartosc.fintech.lms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper
public interface BigDecimalMapper {

    int scale = 4;

    @Named("mapToBigDecimalScale")
    static BigDecimal mapToScale(BigDecimal bigDecimal) {
        return bigDecimal.setScale(scale, RoundingMode.CEILING);
    }

    @Named("mapStringToBigDecimalScale")
    static BigDecimal mapStringToBigDecimalScale(String interestRate) {
        BigDecimal bigDecimal = new BigDecimal(interestRate);
        return bigDecimal.setScale(scale, RoundingMode.CEILING);
    }
}
