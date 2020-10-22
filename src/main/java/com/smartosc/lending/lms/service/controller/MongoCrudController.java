package com.smartosc.lending.lms.service.controller;

import java.util.List;

import com.smartosc.lending.lms.service.dto.Crud;
import com.smartosc.lending.lms.service.dto.FailureResponse;
import com.smartosc.lending.lms.service.entity.CrudEntity;
import com.smartosc.lending.lms.service.service.MongoCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/mongocrud/")
@Api(value = "MongoDB CRUD Controller")
public class MongoCrudController {

	@Autowired
	MongoCrudService service;

	 /**
	 * Get all Crud records
	 * Metdho = Get    
	 */
	@ApiOperation(value = "Listing all Crud rows")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "crud row fechted success", response = CrudEntity.class),
			@ApiResponse(code = 400, message = "Bad request", response = FailureResponse.class) })
	@GetMapping
	@Cacheable("crudList")
	public List<CrudEntity> all() {
		return service.list();
	}

	 /**
     * Create new a crud with DTO directly
     * Method = POST    
     */
	@PostMapping()
	public CrudEntity create(@RequestBody Crud dto) {
		return service.upsert(dto);
	}

	/**
	 * Update a existed crud with DTO directly
	 * Method = POST    
	 */
	@PutMapping(value = "/{id}")
	public CrudEntity update(@RequestBody Crud dto, @PathVariable(required = true) String id) {
		dto.setId(id);
		return service.upsert(dto);
	}
	 /**
     * Get a crud by ID
     * Method = Get
     *     
     **/
	@ApiOperation(value = "Get one Crud by id", notes = "id is mandatory")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "got success", response = CrudEntity.class),
			@ApiResponse(code = 400, message = "Bad request", response = FailureResponse.class),
			@ApiResponse(code = 404, message = "Not Found Exception", response = FailureResponse.class)
			/*
			 * @ApiResponse(code = 500, message = "Internal Server Error", response =
			 * FailureResponse.class)
			 */ })
	@ResponseBody	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Cacheable("crud")
	public CrudEntity get(@PathVariable(required = true) String id) {
		return service.get(id);
	}

	 /**
     * Remove a crud by ID
     * Method = DELETE
     *     
     **/
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Delete success", response = CrudEntity.class),
			@ApiResponse(code = 400, message = "Bad request", response = FailureResponse.class)})
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(required = true) String id) {
		service.delete(id);
	}
	
	 /**
     * Evict banklist from cache. Use this method with any data changed.;
     *     
     */
    @CacheEvict(cacheNames = {"crudList", "crud"}, allEntries = true)
    @DeleteMapping(path = "/cache/evict")
    @ApiOperation(value = "Evict Crud cache")
    public void evictCache() {
        log.info("crud cache has been evicted");
    }
}
