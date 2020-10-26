package com.smartosc.lending.lms.service.controller;

import com.smartosc.lending.lms.service.dto.FailureResponse;
import com.smartosc.lending.lms.service.dto.Response;
import com.smartosc.lending.lms.service.dto.request.BankListDemo;
import com.smartosc.lending.lms.service.dto.response.BankListDemoResponse;
import com.smartosc.lending.lms.service.exception.InternalServiceException;
import com.smartosc.lending.lms.service.service.BankListDemoService;
import com.smartosc.lending.lms.service.util.IConst;
import com.smartosc.lending.lms.service.util.SMFLogger;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/bank")
@Api(value = "BankListDemo Controller")
public class BankListDemoController {

  @Autowired
  BankListDemoService service;

  /**
   * Get all banklistdemo records
   * Metdho = Get
   */
  @ApiOperation(value = "Listing all bank_list_demo")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "banklistdemo list fechted success", response = BankListDemo.class),
      @ApiResponse(code = 400, message = "Bad request", response = FailureResponse.class)})
  @GetMapping
  @Cacheable("banklistAll")
  public Response<List<BankListDemoResponse>> getAllBankListDemo() {
    return Response.success(service.list());
  }

  /**
   * Create new a banklistdemo with DTO directly
   * Method = POST
   */
  @PostMapping
  @SMFLogger
  public Response<BankListDemoResponse> createNew(@RequestBody @Valid BankListDemo dto) {
    return Response.success(service.insertOrUpdate(dto));
  }

  /**
   * Create new a banklistdemo with DTO directly
   * Method = POST
   */
  @PutMapping
  @SMFLogger
  public Response<BankListDemoResponse> updateOne(@RequestBody BankListDemo dto, @PathVariable(required = true) Long id) {
    dto.setId(id);
    return Response.success(service.insertOrUpdate(dto));
  }

  /**
   * Create new a banklistdemo vi parameters
   * Method = POST
   */
  @ApiOperation(value = "Create new BankListDemo", notes = "parameters are not null")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Insert success", response = BankListDemo.class),
      @ApiResponse(code = 400, message = "Require bankname and type parameter", response = FailureResponse.class),
      @ApiResponse(code = 404, message = "Not Found Exception", response = FailureResponse.class)})
  @PostMapping(value = "/newone", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Response<BankListDemoResponse> createNew(@ApiParam(value = "Bankname", required = true) @RequestParam String bankname,
                                                  @ApiParam(value = "Type", required = true) @RequestBody Integer type) {

    if (!IConst.BANKLISTDEMO_TYPES.containsKey(type))
      throw new InternalServiceException(String.format("Type is invalid [%s]", type));
    BankListDemo dto = BankListDemo.builder().bankName(bankname).type(type).build();
    return Response.success(service.insertOrUpdate(dto));
  }

  /**
   * Get a banklistdemo by ID
   * Method = Get
   **/
  @ApiOperation(value = "Get banklistdemo by id", notes = "id is mandatory")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "got success", response = BankListDemo.class),
      @ApiResponse(code = 400, message = "Bad request", response = FailureResponse.class),
      @ApiResponse(code = 404, message = "Not Found Exception", response = FailureResponse.class)
      /*
       * @ApiResponse(code = 500, message = "Internal Server Error", response =
       * FailureResponse.class)
       */})
  @ResponseBody
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Cacheable("banklist")
  public Response<BankListDemoResponse> getBankListDemoById(@PathVariable(required = true) long id) {
    return Response.success(service.getBankListDemoById(id));
  }

  /**
   * Remove a banklistdemo by ID
   * Method = DELETE
   **/
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Delete success", response = BankListDemo.class),
      @ApiResponse(code = 400, message = "Bad request", response = FailureResponse.class)})
  @DeleteMapping("/{id}")
  public void delete(@PathVariable(required = true) long id) {
    service.delete(id);
  }

  /**
   * Evict banklist from cache. Use this method with any data changed.;
   */
  @CacheEvict(cacheNames = {"bankList", "banklistAll"}, allEntries = true)
  @DeleteMapping(path = "/cache/evict")
  @ApiOperation(value = "Evict banklistdemo cache")
  public void evictCache() {
    log.info("banklistdemo cache has been evicted");
  }
}
