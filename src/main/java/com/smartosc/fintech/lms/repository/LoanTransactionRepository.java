package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author minhnd3@smartosc.com
 * @since 10-Nov-20
 */
public interface LoanTransactionRepository extends JpaRepository<LoanTransactionEntity, Long> {
}
