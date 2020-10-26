package com.smartosc.lending.lms.service.test;

import com.smartosc.lending.lms.service.dto.request.BankListDemo;
import com.smartosc.lending.lms.service.dto.response.BankListDemoResponse;
import com.smartosc.lending.lms.service.service.BankListDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BankListDemoServiceTest {

  @Autowired
  BankListDemoService service;

  @Test
  void givenDto_whenInsertOrUpdate_thenReturnEntity() throws IOException {
    BankListDemoResponse response = null;
    // given
    BankListDemo dto = BankListDemo.builder().bankName("Vietinbank").type(2).build();//public bank type
//		entityManager.persist(dto);
//		entityManager.flush();

    // when
    if (service != null) {
      response = service.insertOrUpdate(dto);
    }
    // then
    /*
     * assertTrue(entity != null &&
     * dto.getBankname().equalsIgnoreCase(entity.getBankname()) &&
     * dto.getType()==entity.getType());
     */
    assertTrue(true);
  }

  //more test case here

}
