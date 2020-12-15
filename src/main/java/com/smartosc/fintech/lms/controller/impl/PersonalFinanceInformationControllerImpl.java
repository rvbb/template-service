package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.controller.IPersonalFinanceInformationController;
import com.smartosc.fintech.lms.dto.LoanJobInformationDto;
import com.smartosc.fintech.lms.dto.PersonalFinInfoRequest;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.service.impl.PersonalInformationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class PersonalFinanceInformationControllerImpl implements IPersonalFinanceInformationController {

    private final PersonalInformationServiceImpl personalInformationService;

    @Override
    @SMFLogger
    public Response<List<LoanJobInformationDto>> updateLoanPersonalFinInfo(String uuid, @Valid PersonalFinInfoRequest request) {
        List<LoanJobInformationDto> result = personalInformationService.updatePersonalFinInfo(uuid, request);
        return Response.ok(result);
    }

    @Override
    @SMFLogger
    public Response<LoanJobInformationDto> fetchPersonalFinInfo(String uuid) {
        return Response.ok(personalInformationService.getPersonalFinInfo(uuid));
    }
}