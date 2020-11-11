package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTransactionRepository extends JpaRepository<LoanTransactionEntity, Long> {
}