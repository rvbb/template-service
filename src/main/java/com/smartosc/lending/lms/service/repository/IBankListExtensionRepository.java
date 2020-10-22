package com.smartosc.lending.lms.service.repository;

import com.smartosc.lending.lms.service.entity.BankListDemoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBankListExtensionRepository {

	List<BankListDemoEntity> findByBankName(String bankname);
	
}
