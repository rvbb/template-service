package com.smartosc.fintech.lms.validator;

import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.dto.RepaymentRequestDto;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import org.springframework.stereotype.Component;

@Component
public class RepaymentRequestValidator {
    public void validateRepaymentRequest(RepaymentRequestDto repaymentRequestDto){
        if(repaymentRequestDto.getAmount() == null){
            throw new BusinessServiceException("Amount is empty", ErrorCode.REPAYMENT_AMOUNT_EMPTY);
        }
        if(repaymentRequestDto.getUuid() == null || repaymentRequestDto.getUuid().isEmpty()){
            throw new BusinessServiceException("UUID is empty", ErrorCode.REPAYMENT_UUID_EMPTY);
        }
    }
}
