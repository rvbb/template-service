package com.rvbb.api.template.validator;

import com.rvbb.api.template.common.constant.ErrorCode;
import com.rvbb.api.template.dto.FinanceInfoInput;
import com.rvbb.api.template.dto.FinanceInfoRes;
import com.rvbb.api.template.exception.BizLogicException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FinanceInfoValidator {

    public void checkSimilarWithLast(FinanceInfoRes last, FinanceInfoInput request) {
        double expense = Math.floor(Double.valueOf(request.getExpense()) * 100) / 100;
        double preTaxIncome =  Math.floor(Double.valueOf(request.getPreTaxIncome()) * 100) / 100;
        double lastExpense = Math.floor(last.getExpense().doubleValue() * 100)/ 100;
        double lastPreTaxIncome =  Math.floor(last.getPreTaxIncome().doubleValue() * 100) / 100;
        log.info("validate, expense {} == {}, income {}={}", expense,lastExpense,preTaxIncome,lastPreTaxIncome);
        if (last.getCompanyAddress().equalsIgnoreCase(request.getCompanyAddress())
                && last.getCompanyName().equalsIgnoreCase(request.getCompanyName())
                && expense == lastExpense
                && preTaxIncome == lastPreTaxIncome) {
            throw new BizLogicException("Your inputted data is similar(not care sensitive case) with last finance information", ErrorCode.NOT_ALLOWED);
        }
    }

    public void validateInputType(FinanceInfoInput request) {
        try {
            Double.valueOf(request.getPreTaxIncome());
            Double.valueOf(request.getExpense());
        }catch (NumberFormatException e){
            throw new BizLogicException("The 'expense' or/and 'preTaxIncome' field must be numeric and length MUST <= 15", HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }
    }

}
