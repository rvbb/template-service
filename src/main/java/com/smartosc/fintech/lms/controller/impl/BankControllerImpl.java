package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.controller.BankController;
import com.smartosc.fintech.lms.dto.BankDto;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BankControllerImpl implements BankController {

  private final BankService bankService;

  @Autowired
  public BankControllerImpl(BankService bankService) {
    this.bankService = bankService;
  }

  /**
   * Get all bank records
   * Method = Get
   */
  @Cacheable("banks_cache")
  @Override
  public Response<Page<BankDto>> get(int page, int size) {
    return Response.ok(bankService.get(PageRequest.of(page, size)));
  }

  /**
   * Get a bank by ID
   * Method = Get
   **/
  @Cacheable("bank_cache")
  @Override
  public Response<BankDto> get(long id) {
    return Response.ok(bankService.get(id));
  }

  /**
   * Create new a bank with DTO directly
   * Method = POST
   */
  @Override
  @SMFLogger
  public Response<BankDto> create(BankDto bankDto) {
    return Response.ok(bankService.create(bankDto));
  }

  /**
   * Create new a bank with DTO directly
   * Method = POST
   */
  @Override
  @SMFLogger
  public Response<BankDto> update(long id, BankDto bankDto) {
    return Response.ok(bankService.update(id, bankDto));
  }

  /**
   * Remove a bank by ID
   * Method = DELETE
   **/
  public void delete(long id) {
    bankService.delete(id);
  }

  /**
   * Evict banks from cache. Use this method with any data changed.;
   */
  @CacheEvict(cacheNames = {"banks_cache", "bank_cache"}, allEntries = true)
  public void evictCache() {
    log.info("Bank cache has been evicted");
  }
}
