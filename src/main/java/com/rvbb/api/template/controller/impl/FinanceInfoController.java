package com.rvbb.api.template.controller.impl;

import com.rvbb.api.template.common.util.LogIt;
import com.rvbb.api.template.controller.IFinanceInfoController;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoFilterInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.api.template.dto.Response;
import com.rvbb.api.template.service.IFinanceInfoService;
import com.rvbb.api.template.validator.FinanceInfoValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class FinanceInfoController implements IFinanceInfoController {

    private final IFinanceInfoService loanFinInfoService;
    private final FinanceInfoValidator loanFinInfoValidator;

    @Override
    @LogIt
    public Response<FinanceInfoRes> create(@Valid FinanceInfoInput request) {
        FinanceInfoRes result = loanFinInfoService.create(request);
        return Response.ok(result);
    }

    @Override
    public Response<FinanceInfoRes> getLast() {
        return Response.ok(loanFinInfoService.getLast());
    }

    @Override
    @LogIt
    public Response<FinanceInfoRes> update(String uuid, @Valid FinanceInfoInput request) {
        FinanceInfoRes lastFinInfo = loanFinInfoService.get(uuid);
        loanFinInfoValidator.validateInputType(request);
        loanFinInfoValidator.checkSimilarWithLast(lastFinInfo, request);
        FinanceInfoRes result = loanFinInfoService.update(uuid, request);
        return Response.ok(result);
    }

    @Override
    @LogIt
    public Response<FinanceInfoRes> get(String uuid) {
        return Response.ok(loanFinInfoService.get(uuid));
    }

    @Override
    @LogIt
    public Response<List<FinanceInfoRes>> list() {
        return Response.ok(loanFinInfoService.list());
    }

    @Override
    @LogIt
    public Response<FinanceInfoRes> del(String uuid) {
        return Response.ok(loanFinInfoService.del(uuid));
    }

    @Override
    @LogIt
    public Response<PagedListHolder<FinanceInfoRes>> filter(FinanceInfoFilterInput filter) {
        return Response.ok(loanFinInfoService.doFilter(filter));
    }

}