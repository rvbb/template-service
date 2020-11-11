package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author minhnd3@smartosc.com
 * @since 10-Nov-20
 */
@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long> {
    Optional<LoanApplicationEntity> findByUuid(String uuid);

}
