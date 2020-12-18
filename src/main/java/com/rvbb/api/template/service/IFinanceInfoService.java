package com.rvbb.api.template.service;

import com.rvbb.api.template.dto.FinanceInfoInput;
import com.rvbb.api.template.dto.FinanceInfoRes;

import java.util.List;

public interface IFinanceInfoService {

    List<FinanceInfoRes> list();

    FinanceInfoRes update(String id, FinanceInfoInput request);

    FinanceInfoRes create(FinanceInfoInput request);

    FinanceInfoRes get(String id);

    FinanceInfoRes del(String id);

    FinanceInfoRes getLast(String uuid);
}
