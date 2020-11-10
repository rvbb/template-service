package com.smartosc.fintech.lms.repository;


import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInformationRepository extends JpaRepository<LoanPersonalInformationEntity, Long> {

}
