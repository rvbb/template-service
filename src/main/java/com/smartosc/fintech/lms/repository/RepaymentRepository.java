package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.RepaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepaymentRepository extends JpaRepository<RepaymentEntity, Long> {
}
