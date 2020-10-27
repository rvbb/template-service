package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.BankDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankService {

  Page<BankDto> get(Pageable pageable);

  BankDto get(long id);

  BankDto create(BankDto bankDto);

  BankDto update(long id, BankDto bankDto);

  void delete(long id);
}
