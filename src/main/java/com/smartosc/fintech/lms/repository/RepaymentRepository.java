package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.RepaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author minhnd3@smartosc.com
 * @since 10-Nov-20
 */
public interface RepaymentRepository extends JpaRepository<RepaymentEntity, Long> {
}
