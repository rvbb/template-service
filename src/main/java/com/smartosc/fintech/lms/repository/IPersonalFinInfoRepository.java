package com.smartosc.fintech.lms.repository;


import com.smartosc.fintech.lms.entity.LoanJobInformationEntity;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPersonalFinInfoRepository extends JpaRepository<LoanJobInformationEntity, Long> {

    Optional<LoanJobInformationEntity> findByLoanApplicationUuid(String uuid);

}
