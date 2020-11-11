package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.controller.PersonalInformationController;
import com.smartosc.fintech.lms.dto.PersonalInformationDto;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.service.PersonalInformationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PersonalInformationControllerImpl implements PersonalInformationController {

    private final PersonalInformationService personalInformationService;

    @Override
    @SMFLogger
    public Response<List<PersonalInformationDto>>
        updateLoanPersonalInformation(String uuid, PersonalInformationDto personalInformationDto) {
        List<PersonalInformationDto> result = personalInformationService.updateLoanPersonalInformation(uuid, personalInformationDto);
        return Response.ok(result);
    }
}