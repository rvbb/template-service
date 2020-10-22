package com.smartosc.lending.lms.service.repository.impl;

import com.smartosc.lending.lms.service.entity.BankListDemoEntity;
import com.smartosc.lending.lms.service.repository.IBankListExtensionRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BankListRepositoryImpl implements IBankListExtensionRepository {
	public List<BankListDemoEntity> findByBankName(String bankname){
		log.info("Bank Name = {}", bankname);
		return new ArrayList<>();//TODO implement extension biz here
	}
}
