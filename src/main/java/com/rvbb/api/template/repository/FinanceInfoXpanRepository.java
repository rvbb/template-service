package com.rvbb.api.template.repository;


import com.rvbb.api.template.dto.financeinfo.FinanceInfoFilterInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoRes;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceInfoXpanRepository {

    boolean updateByStatus(FinanceInfoInput request, Short status);
    PagedListHolder<FinanceInfoRes> search(FinanceInfoFilterInput filter);

}
