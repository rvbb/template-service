package com.rvbb.api.template.repository;


import com.rvbb.api.template.dto.FinanceInfoInput;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceInfoXpanRepository {

    boolean updateByStatus(FinanceInfoInput request, Short status);
}
