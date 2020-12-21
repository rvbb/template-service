package com.rvbb.api.template.common.util;

import com.rvbb.api.template.common.constant.FieldName;
import com.rvbb.api.template.dto.projection.IFinanceInfoProjection;
import com.rvbb.api.template.entity.FinanceInfoEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtil {

    private static volatile SecureRandom numberGenerator = null;
    private static final long MSB = 0x3200000000000000L;

    public static String unique() {
        SecureRandom secureRandom = numberGenerator;
        if (secureRandom == null) {
            numberGenerator = secureRandom = new SecureRandom();
        }
        return Long.toHexString(MSB | secureRandom.nextLong()) + Long.toHexString(MSB | secureRandom.nextLong());
    }

    public static List<FinanceInfoEntity> fromResultList(List<Object[]> objectList) {
        List<FinanceInfoEntity> converted = new ArrayList<>();
        objectList.stream().forEach(entityAsObjectArray -> {
            log.info("FinanceInfoEntity as Object array=[{}]", entityAsObjectArray);
            FinanceInfoEntity entity = FinanceInfoEntity.builder()
                    .id(Long.valueOf(String.valueOf(entityAsObjectArray[0])))
                    .status(Byte.valueOf(String.valueOf(entityAsObjectArray[1])))
                    .lastUpdate(DateTimeUtil.string2Date(String.valueOf(entityAsObjectArray[2]), false))
                    .companyName(String.valueOf(entityAsObjectArray[3]))
                    .companyAddress(String.valueOf(entityAsObjectArray[4]))
                    .uuid(String.valueOf(entityAsObjectArray[5]))
                    .preTaxIncome(new BigDecimal(Double.valueOf(String.valueOf(entityAsObjectArray[6]))))
                    .expense(new BigDecimal(Double.valueOf(String.valueOf(entityAsObjectArray[7]))))
                    .build();
            converted.add(entity);
        });

        return converted;
    }
}
