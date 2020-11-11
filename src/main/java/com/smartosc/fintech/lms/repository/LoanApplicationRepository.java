package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author minhnd3@smartosc.com
 * @since 10-Nov-20
 */
@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long> {
    LoanApplicationEntity findLoanApplicationEntityByUuid(String uuid);

}
