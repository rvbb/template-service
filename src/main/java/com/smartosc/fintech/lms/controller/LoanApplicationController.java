package com.smartosc.fintech.lms.controller;


import com.smartosc.fintech.lms.controller.handler.ApiError;
import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/loan-application")
@Api(value = "Loan Application Api")
public interface LoanApplicationController {

    @ApiOperation(value = "Update loan application by uuid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = LoanApplicationDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping("/{uuid}")
    public Response<LoanApplicationDto> getLoanApplication(@PathVariable String uuid);
}
