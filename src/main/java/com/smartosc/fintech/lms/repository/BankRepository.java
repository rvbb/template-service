package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, Long> {
}
