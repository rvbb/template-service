package com.smartosc.fintech.lms.controller;

import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.dto.Response;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/loan-personal-information")
@Api(value = "Loan personal information Api")
public interface LoanPersonalInformationController {

    @PutMapping("/{id}")
    public Response<LoanPersonalInformationDto> updateLoanPersonalInformation(Integer id);

}
