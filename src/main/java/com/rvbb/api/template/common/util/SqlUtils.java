package com.rvbb.api.template.common.util;

import com.rvbb.api.template.common.constant.CommonFieldName;
import com.rvbb.api.template.common.constant.FinanceInfoFieldName;
import com.rvbb.api.template.common.constant.FinanceInfoStatus;
import com.rvbb.api.template.common.constant.SqlOperationName;
import com.rvbb.api.template.entity.FinanceInfoEntity;
import com.rvbb.api.template.repository.spec.SpecCustom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.type.SpecialOneToOneType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlUtils {

    public static List<FinanceInfoEntity> fromResultList(List<Object[]> objectList) {
        List<FinanceInfoEntity> converted = new ArrayList<>();
        objectList.stream().forEach(entityAsObjectArray -> {
            log.info("FinanceInfoEntity as Object array=[{}]", entityAsObjectArray);
            FinanceInfoEntity entity = FinanceInfoEntity.builder()
                    .id(Long.valueOf(String.valueOf(entityAsObjectArray[0])))
                    .status(Byte.valueOf(String.valueOf(entityAsObjectArray[1])))
                    .lastUpdate(DateTimeUtil.string2Date(String.valueOf(entityAsObjectArray[2]), true))
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

    public static List<Sort.Order> buildOrder(String[] sort) {
        List<Sort.Order> orderList = new ArrayList<>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] fieldAndVal = sortOrder.split(",");
                orderList.add(new Sort.Order(getSortValue(fieldAndVal[1]), fieldAndVal[0]));
            }
        } else {
            orderList.add(new Sort.Order(getSortValue(sort[1]), sort[0]));
        }

        return orderList;
    }

    public static Sort.Direction getSortValue(String direction) {
        return direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : (direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : null);
    }

    /**
     * sort=col1,desc&sort=col2,asc&sort=col3,asc
     * need to validate before in Controller
     **/
    public static Sort buildSort(String[] sort) {
        Sort result = Sort.by(Sort.Direction.ASC, CommonFieldName.ID.toString());
        if (ObjectUtils.isEmpty(sort)) {
            return result;
        }

        for (String oneSort : sort) {
            String[] fieldAndVal = oneSort.split(",");
            if (ObjectUtils.isNotEmpty(getSortValue(sort[1]))) {
                result.by(getSortValue(fieldAndVal[1]), fieldAndVal[0]);
            }
        }
        return result;
    }

    /**
     * condition=equal,col1,1&condition=greater,col2,abc
     * need to validate before in Controller
     **/
    public static <T> Specification buildSpec(String[] condition, T claxx) {
        Specification<T> result = null;
        List<SpecCustom> and = new ArrayList<>();
        List<SpecCustom> or = new ArrayList<>();
        if (claxx instanceof FinanceInfoStatus) {
            FinanceInfoStatus financeInfoStatus = (FinanceInfoStatus) claxx;
            for (String oneCondition : condition) {
                String[] oneCondition_ = oneCondition.split(",");
                String operator = oneCondition_[0];
                String field = oneCondition_[1];
                String val = oneCondition_[2];
                switch (SqlOperationName.valueOf(operator.toLowerCase())) {
                    case equal:
                        if (ObjectUtils.isNotEmpty(result)) {
                            result = result.and(SpecCustom.equal(field, val));
                        } else {
                            result = Specification.where(SpecCustom.equal(field, val));
                        }
                        break;
                    case like:
                        if (ObjectUtils.isNotEmpty(result)) {
                            result = result.and(SpecCustom.like(field, val));
                        } else {
                            result = Specification.where(SpecCustom.like(field, val));
                        }
                        break;
                    case greaterorequal:
                        break;
                    case less:
                        break;
                }
            }
        }
        return result;
    }
}