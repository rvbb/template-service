package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author minhnd3@smartosc.com
 * @since 10-Nov-20
 */
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long> {
}
