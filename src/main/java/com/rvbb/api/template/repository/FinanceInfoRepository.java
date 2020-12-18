package com.rvbb.api.template.repository;


import com.rvbb.api.template.entity.FinanceInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinanceInfoRepository extends JpaRepository<FinanceInfoEntity, Long> {

    Optional<FinanceInfoEntity> findByUuid(String uuid);

    @Query(value = "SELECT * FROM loan_job_information l WHERE l.uuid = :uuid and id = (select max(ll.id) from loan_job_information ll)", nativeQuery = true)
    FinanceInfoEntity getLastFinInfoByUuid(@Param("uuid") String uuid);

}
