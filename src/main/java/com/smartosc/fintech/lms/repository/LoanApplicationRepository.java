package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long> {
    Optional<LoanApplicationEntity> findLoanApplicationEntityByUuid(String uuid);

    @Query(value = "SELECT * FROM `lms-service`.loan_application where user_id=?1", nativeQuery = true)
    List<LoanApplicationEntity> findLoanApplicationByUser(long userId);

}
