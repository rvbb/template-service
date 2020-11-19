package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.controller.LoanApplicationController;
import com.smartosc.fintech.lms.dto.BriefLoanDto;
import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.service.LoanApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LoanApplicationControllerImpl implements LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    @Override
    public Response<LoanApplicationDto> getLoanApplication(String uuid) {
        LoanApplicationDto loanApplicationDto = loanApplicationService.findLoanApplicationEntityByUuid(uuid);
        return Response.ok(loanApplicationDto);
    }

    @Override
    public Response<List<BriefLoanDto>> getListLoanApplication(long userId) {
        List<BriefLoanDto> loanApplicationDtos = loanApplicationService.findLoanApplicationByUser(userId);
        return Response.ok(loanApplicationDtos);
    }

}
