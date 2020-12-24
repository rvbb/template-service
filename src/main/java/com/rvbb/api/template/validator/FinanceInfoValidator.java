package com.rvbb.api.template.validator;

import com.rvbb.api.template.common.constant.ErrorCode;
import com.rvbb.api.template.common.constant.FinanceInfoFieldName;
import com.rvbb.api.template.common.constant.SqlOperationName;
import com.rvbb.api.template.common.util.SqlUtils;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.api.template.exception.BizLogicException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FinanceInfoValidator {

    public void checkSimilarWithLast(FinanceInfoRes old, FinanceInfoInput request) {
        double expense = Math.floor(Double.valueOf(request.getExpense()) * 100) / 100;
        double preTaxIncome = Math.floor(Double.valueOf(request.getPreTaxIncome()) * 100) / 100;
        double lastExpense = Math.floor(old.getExpense().doubleValue() * 100) / 100;
        double lastPreTaxIncome = Math.floor(old.getPreTaxIncome().doubleValue() * 100) / 100;
        log.info("validate, expense {} == {}, income {}={}", expense, lastExpense, preTaxIncome, lastPreTaxIncome);
        if (old.getCompanyAddress().equalsIgnoreCase(request.getCompanyAddress())
                && old.getCompanyName().equalsIgnoreCase(request.getCompanyName())
                && expense == lastExpense
                && preTaxIncome == lastPreTaxIncome
                && request.getStatus() == old.getStatus()) {
            throw new BizLogicException("Your inputted data is similar(not care sensitive case) with last finance information", ErrorCode.NOT_ALLOWED.val);
        }
    }

    public void validateInputType(FinanceInfoInput request) {
        try {
            Double.valueOf(request.getPreTaxIncome());
            Double.valueOf(request.getExpense());
        } catch (NumberFormatException e) {
            throw new BizLogicException("The 'expense' or/and 'preTaxIncome' field must be numeric and length MUST <= 15", HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }
    }

    public void validateFilter(String sort[], String condition[], int page, int size) {
        StringBuilder errorMessage = new StringBuilder();
        if (ObjectUtils.isNotEmpty(sort)) {
            for (String oneSort : sort) {
                String[] fieldAndVal = oneSort.split(",");
                if (fieldAndVal.length != 2 || ObjectUtils.isEmpty(SqlUtils.getSortValue(sort[1])) || StringUtils.isEmpty(fieldAndVal[0])) {
                    errorMessage.append("The sort [" + oneSort + "] is invalid. ");
                } else if (StringUtils.isEmpty(sort[1]) || !FinanceInfoFieldName.contains(sort[1])) {
                    errorMessage.append("The sort is not supported [" + SqlOperationName.asString() + "] is invalid. ");
                }

            }
            if (errorMessage.length() > 0) {
                errorMessage.append("The 'sort' need to follow format sort=col1,desc&sort=col2,asc,col3,asc");
            }
        }
        if (ObjectUtils.isNotEmpty(sort)) {
            for (String oneCondition : condition) {
                String[] fieldAndVal = oneCondition.split(",");
                if (fieldAndVal.length != 3 || ObjectUtils.isEmpty(SqlUtils.getSortValue(sort[1])) || StringUtils.isEmpty(fieldAndVal[0])
                        || SqlOperationName.values().toString().equalsIgnoreCase(sort[1])) {
                    errorMessage.append("The parameter [" + oneCondition + "] is invalid. ");

                } else if (StringUtils.isEmpty(sort[1]) || !SqlOperationName.contains(sort[1])) {
                    errorMessage.append("The condition is not supported [" + SqlOperationName.asString() + "] is invalid. ");
                }
                if (errorMessage.length() > 0) {
                    errorMessage.append("The 'condition' need to follow format condition=equal,col1,1&condition=greater,col2,abc");
                }
            }

            if (errorMessage.length() > 0) {
                throw new BizLogicException("", ErrorCode.INVALID_INPUT.val);
            }
        }
    }
}
