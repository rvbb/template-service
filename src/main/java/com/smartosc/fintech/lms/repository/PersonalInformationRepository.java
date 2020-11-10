package com.smartosc.fintech.lms.repository;


import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalInformationRepository extends JpaRepository<LoanPersonalInformationEntity, Long> {

    @Query("select lp from LoanPersonalInformationEntity lp where lp.loanApplication.uuid = :uuid")
    Optional<LoanPersonalInformationEntity> findPersonalInformationbyLoanAppliaction(@Param("uuid") String uuid);

}
