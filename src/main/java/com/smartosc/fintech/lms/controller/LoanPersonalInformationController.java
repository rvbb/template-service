package com.smartosc.fintech.lms.controller;

import com.smartosc.fintech.lms.controller.handler.ApiError;
import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/loan-personal-information")
@Api(value = "Loan personal information Api")
public interface LoanPersonalInformationController {

    @ApiOperation(value = "Update loan personal information by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = LoanPersonalInformationDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @PutMapping("/{id}")
    public Response<LoanPersonalInformationDto> updateLoanPersonalInformation(@PathVariable long id,@Valid @RequestBody LoanPersonalInformationDto loanPersonalInformationDto);



}
