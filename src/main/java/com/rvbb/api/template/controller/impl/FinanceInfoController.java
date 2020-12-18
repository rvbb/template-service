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
import java.util.List;

@RestController
@AllArgsConstructor
public class FinanceInfoController implements IFinanceInfoController {

    private final IFinanceInfoService loanFinInfoService;
    private final FinanceInfoValidator loanFinInfoValidator;

    @Override
    public Response<FinanceInfoRes> create(@Valid FinanceInfoInput request) {
        FinanceInfoRes lastFinInfo = loanFinInfoService.getLast();
        loanFinInfoValidator.validateInputType(request);
        loanFinInfoValidator.checkSimilarWithLast(lastFinInfo, request);
        FinanceInfoRes result = loanFinInfoService.create(request);
        return Response.ok(result);
    }

    @Override
    public Response<FinanceInfoRes> getLast() {
        return Response.ok(loanFinInfoService.getLast());
    }

    @Override
    public Response<FinanceInfoRes> update(String uuid, @Valid FinanceInfoInput request) {
        FinanceInfoRes lastFinInfo = loanFinInfoService.getLast();
        loanFinInfoValidator.validateInputType(request);
        loanFinInfoValidator.checkSimilarWithLast(lastFinInfo, request);
        FinanceInfoRes result = loanFinInfoService.update(uuid, request);
        return Response.ok(result);
    }

    @Override
    public Response<FinanceInfoRes> get(String uuid) {
        return Response.ok(loanFinInfoService.get(uuid));
    }

    @Override
    public Response<List<FinanceInfoRes>> list() {
        return Response.ok(loanFinInfoService.list());
    }

    @Override
    public Response<FinanceInfoRes> del(String uuid) {
        return Response.ok(loanFinInfoService.del(uuid));
    }
}