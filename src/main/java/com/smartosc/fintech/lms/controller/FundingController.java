package com.smartosc.fintech.lms.controller;

import com.smartosc.fintech.lms.controller.handler.ApiError;
import com.smartosc.fintech.lms.dto.FundingRequest;
import com.smartosc.fintech.lms.dto.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/funding")
@Api(value = "Funding Controller")
public interface FundingController {
    @ApiOperation(value = "Make a disbursement corresponding a loan to customer", notes = "id is mandatory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FundingRequest.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
    })
    @PostMapping
    Response<Object> makeFunding(@RequestBody FundingRequest request);
}
