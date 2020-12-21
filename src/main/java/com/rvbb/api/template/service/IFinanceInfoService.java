package com.rvbb.api.template.service;

import com.rvbb.api.template.dto.financeinfo.FinanceInfoFilterInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoRes;
import org.springframework.beans.support.PagedListHolder;

import java.util.List;

public interface IFinanceInfoService {

    List<FinanceInfoRes> list();

    FinanceInfoRes update(String uuid, FinanceInfoInput request);

    FinanceInfoRes create(FinanceInfoInput request);

    FinanceInfoRes get(String uuid);

    FinanceInfoRes del(String uuid);

    FinanceInfoRes getLast();

    PagedListHolder<FinanceInfoRes> doFilter(FinanceInfoFilterInput filter);
}
