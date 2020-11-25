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

    @Query(value = "SELECT *, case status\n" +
            "when  4 then 1\n" +
            "when  3 then 2\n" +
            "when  1 then 3\n" +
            "when  5 then 4\n" +
            "when  2 then 5\n" +
            "end as temp\n" +
            "FROM `lms-service`.loan_application\n" +
            "where status!=0\n" +
            "and user_id=?1\n" +
            "order by temp asc;", nativeQuery = true)
    List<LoanApplicationEntity> findLoanApplicationEntityByUserIdAndStatusNotDrop(long userId);

}
