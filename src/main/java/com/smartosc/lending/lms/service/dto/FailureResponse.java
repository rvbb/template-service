package com.smartosc.lending.lms.service.dto;

import io.swagger.annotations.ApiModelProperty;

public class FailureResponse {
    
    @ApiModelProperty(notes = "Failure code", example = "404")
    public int code = 0;
    
    @ApiModelProperty(notes = "Short failure describe", example = "Not Found")
    public String fail = "";
    
    @ApiModelProperty(notes = "Detail of failure", example = "Your accout balance is not enough.")
    public String message = "";
    
    @ApiModelProperty(notes = "path to resource maded failure", example = "/webback/bankplus/bank/transactionDemoAdd")
    public String path = "";
    
    @ApiModelProperty(notes = "Moment of failure, display by timestamp format", example = "2019-09-26T01:49:07.140+0000")
    public int moment = 0;
}
