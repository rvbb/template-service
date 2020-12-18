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

@Api(value = "Loan financial information API")
@RequestMapping("finance")
public interface IFinanceInfoController {
    @ApiOperation(value = "Update Loan financial information by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @PutMapping("/{uuid}")
    Response<FinanceInfoRes> saveLoanFinInfo(@PathVariable String uuid, @Valid @RequestBody FinanceInfoInput request);

    @ApiOperation(value = "Get Loan financial information by loan application with uuid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoRes.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @GetMapping("/{uuid}")
    Response<FinanceInfoRes> fetchLastLoanFinInfo(@PathVariable("uuid") String uuid);
}
