package com.rvbb.api.template.repository.impl;


import com.rvbb.api.template.common.constant.TableName;
import com.rvbb.api.template.dto.FinanceInfoInput;
import com.rvbb.api.template.repository.FinanceInfoXpanRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;

@Repository
public class FinanceInfoXpanRepositoryImpl implements FinanceInfoXpanRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean updateByStatus(FinanceInfoInput request, Short status) {
        String updateQuery = "update "+ TableName.FINANCE_INFO.toString().toLowerCase() +" l " +
                "set l.company_address = ?1, l.company_name = ?2,l.pre_tax_income = ?3, l.expense = ?4, l.last_update = ?5 " +
                "where l.status = ?6";
        Query query = entityManager.createNativeQuery(updateQuery);
        query.setParameter(1, request.getCompanyAddress());
        query.setParameter(2, request.getCompanyName());
        query.setParameter(3, request.getPreTaxIncome());
        query.setParameter(4, request.getExpense());
        query.setParameter(5, new Date());
        query.setParameter(6, status);
        entityManager.flush();
        Integer countUpdated = query.executeUpdate();
        entityManager.clear();
        return countUpdated > 0;
    }

}
