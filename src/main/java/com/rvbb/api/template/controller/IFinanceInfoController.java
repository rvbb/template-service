package com.rvbb.api.template.controller;

import com.rvbb.api.template.controller.handler.Error;
import com.rvbb.api.template.dto.FinanceInfoInput;
import com.rvbb.api.template.dto.FinanceInfoRes;
import com.rvbb.api.template.dto.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Loan financial information API")
@RequestMapping("finance")
public interface IFinanceInfoController {
    @ApiOperation(value = "Create")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @PostMapping
    Response<FinanceInfoRes> create(@Valid @RequestBody FinanceInfoInput request);

    @ApiOperation(value = "Get last")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoRes.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @GetMapping("/last")
    Response<FinanceInfoRes> getLast();

    @ApiOperation(value = "Update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @PutMapping("/{uuid}")
    Response<FinanceInfoRes> update(@PathVariable String uuid, @Valid @RequestBody FinanceInfoInput request);

    @ApiOperation(value = "Read One")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @GetMapping("/{uuid}")
    Response<FinanceInfoRes> get(@PathVariable String uuid);

    @ApiOperation(value = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @GetMapping("/list")
    Response<List<FinanceInfoRes>> list();

    @ApiOperation(value = "Delete One")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @DeleteMapping("/{uuid}")
    Response<FinanceInfoRes> del(@PathVariable String uuid);

}
