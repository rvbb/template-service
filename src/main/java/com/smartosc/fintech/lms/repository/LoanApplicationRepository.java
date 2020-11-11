package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long> {

  Optional<LoanApplicationEntity> findLoanApplicationEntityByUuid(String uuid);
}
