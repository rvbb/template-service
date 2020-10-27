package com.smartosc.fintech.lms.controller;

import com.smartosc.fintech.lms.dto.BankDto;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.controller.handler.ApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/banks")
@Api(value = "Bank Controller")
public interface BankController {

  /**
   * Get all bank records
   * Method = Get
   */
  @ApiOperation(value = "Listing all bank")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "bank list fechted success", response = Page.class),
      @ApiResponse(code = 400, message = "Bad request", response = ApiError.class)
  })
  @GetMapping
  Response<Page<BankDto>> get(@RequestParam int page, @RequestParam int size);

  /**
   * Get a bank by ID
   * Method = Get
   **/
  @ApiOperation(value = "Get banks by id", notes = "id is mandatory")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BankDto.class),
      @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
      @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class)
  })
  @GetMapping("/{id}")
  Response<BankDto> get(@PathVariable long id);

  /**
   * Create new a bank with DTO directly
   * Method = POST
   */
  @ApiOperation(value = "Create a bank by id", notes = "id is mandatory")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BankDto.class),
      @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
  })
  @PostMapping
  Response<BankDto> create(@RequestBody BankDto bankDto);

  /**
   * Update a bank with DTO directly
   * Method = POST
   */
  @ApiOperation(value = "Update a bank by id", notes = "id is mandatory")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BankDto.class),
      @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
      @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class)
  })
  @PutMapping("/{id}")
  Response<BankDto> update(@PathVariable long id, @RequestBody BankDto bankDto);

  /**
   * Remove a bank by ID
   * Method = DELETE
   **/
  @ApiOperation(value = "Delete a bank by id", notes = "id is mandatory")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "success", response = BankDto.class),
      @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
      @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class)
  })
  @DeleteMapping("/{id}")
  void delete(@PathVariable long id);

  /**
   * Evict bank from cache. Use this method with any data changed.;
   */
  @ApiOperation(value = "Evict bank cache")
  @DeleteMapping(path = "/cache/evict")
  void evictCache();
}
