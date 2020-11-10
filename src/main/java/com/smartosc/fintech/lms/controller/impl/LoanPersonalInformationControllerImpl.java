package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.controller.LoanPersonalInformationController;
import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.service.LoanPersonalInformationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class LoanPersonalInformationControllerImpl implements LoanPersonalInformationController {

    private LoanPersonalInformationService loanPersonalInformationService;

    @Override
    public Response<LoanPersonalInformationDto> updateLoanPersonalInformation(long id, LoanPersonalInformationDto loanPersonalInformationDto) {

        LoanPersonalInformationDto result = loanPersonalInformationService.updateLoanPersonalInformation(id, loanPersonalInformationDto);
        return Response.ok(result);
    }
}
