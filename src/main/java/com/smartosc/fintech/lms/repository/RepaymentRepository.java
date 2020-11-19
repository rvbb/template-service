package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.RepaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepaymentRepository extends JpaRepository<RepaymentEntity, Long> {
    Optional<RepaymentEntity> findFirstByUuid(String uuid);

    @Query(value = "SELECT * FROM `lms-service`.repayment where loan_application_key=?1 order by due_date desc;", nativeQuery = true)
    List<RepaymentEntity> findRepaymentOfLoanOrderByDuedate(String loanKey);

}
