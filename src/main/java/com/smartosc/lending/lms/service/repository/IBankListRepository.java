package com.smartosc.lending.lms.service.repository;

import com.smartosc.lending.lms.service.entity.BankListDemoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankListRepository extends CrudRepository<BankListDemoEntity, Long> {
}
