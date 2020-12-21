package com.rvbb.api.template.repository;


import com.rvbb.api.template.entity.FinanceInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinanceInfoRepository extends JpaRepository<FinanceInfoEntity, Long> {

    Optional<FinanceInfoEntity> findByUuid(String uuid);

    @Query(value = "SELECT * FROM finance_info l WHERE id = (select max(ll.id) from finance_info ll)", nativeQuery = true)
    FinanceInfoEntity getLast();

}
