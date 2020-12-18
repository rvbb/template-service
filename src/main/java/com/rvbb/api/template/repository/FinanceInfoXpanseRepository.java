package com.rvbb.api.template.repository;


import com.rvbb.api.template.dto.FinanceInfoInput;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceInfoXpanseRepository {

    boolean updateByLoanApplicationId(FinanceInfoInput request, long loanApplicationId);
    boolean updateLastFinInfoByLoanApplicationId(FinanceInfoInput request, long loanApplicationId);
}
