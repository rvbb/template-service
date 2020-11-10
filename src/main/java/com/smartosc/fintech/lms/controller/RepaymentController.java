package com.smartosc.fintech.lms.controller;

import com.smartosc.fintech.lms.controller.handler.ApiError;
import com.smartosc.fintech.lms.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author minhnd3@smartosc.com
 * @since 10-Nov-20
 */
@RequestMapping(path = "/repayment")
@Api(value = "Repayment API")
public interface RepaymentController {
    @ApiOperation(value = "Make a repayment transaction of a loan", notes = "id is mandatory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RepaymentResponseDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @PostMapping
    Response<RepaymentResponseDto> repayLoan(@RequestBody RepaymentRequestDto repaymentRequestDto);

    @ApiOperation(value = "Calculate interest amount of a loan")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = InterestAmountDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class)
    })
    @GetMapping(path = "calculate-interest-amount")
    Response<InterestAmountDto> calculateInterestAmount(@RequestParam int id);
}
