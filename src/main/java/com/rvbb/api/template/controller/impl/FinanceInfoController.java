package com.rvbb.api.template.controller.impl;

import com.rvbb.api.template.common.util.LogIt;
import com.rvbb.api.template.dto.FinanceInfoInput;
import com.rvbb.api.template.dto.FinanceInfoRes;
import com.rvbb.api.template.dto.Response;
import com.rvbb.api.template.validator.FinanceInfoValidator;
import com.rvbb.api.template.controller.IFinanceInfoController;
import com.rvbb.api.template.service.IFinanceInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class FinanceInfoController implements IFinanceInfoController {

    private final IFinanceInfoService loanFinInfoService;
    private final FinanceInfoValidator loanFinInfoValidator;
    @Override
    @LogIt
    public Response<FinanceInfoRes> saveLoanFinInfo(String uuid, @Valid FinanceInfoInput request) {
        FinanceInfoRes lastFinInfo = loanFinInfoService.getLastLoanFinInfo(uuid);
        loanFinInfoValidator.validateInputType(request);
        loanFinInfoValidator.checkSimilarWithLast(lastFinInfo, request);
        FinanceInfoRes result = loanFinInfoService.insertNewLoanFinInfo(uuid, request);
        return Response.ok(result);
    }

    @Override
    @LogIt
    public Response<FinanceInfoRes> fetchLastLoanFinInfo(String uuid) {
        return Response.ok(loanFinInfoService.getLastLoanFinInfo(uuid));
    }
}