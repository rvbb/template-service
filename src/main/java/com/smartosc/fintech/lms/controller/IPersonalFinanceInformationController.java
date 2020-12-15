package com.smartosc.fintech.lms.controller;

import com.smartosc.fintech.lms.controller.handler.ApiError;
import com.smartosc.fintech.lms.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Loan personal financial information API")
@RequestMapping("personal-fininfo")
public interface IPersonalFinanceInformationController {

    @ApiOperation(value = "Update personal financial information by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PersonalFinInfoRequest.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @PutMapping("/{uuid}")
    Response<List<LoanJobInformationDto>> updateLoanPersonalFinInfo(@PathVariable String uuid, @Valid @RequestBody PersonalFinInfoRequest request);

    @ApiOperation(value = "Get personal financial information by loan application with uuid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = LoanJobInformationDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping("/{uuid}")
    Response<LoanJobInformationDto> fetchPersonalFinInfo(@PathVariable("uuid") String uuid);
}
