package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.controller.PersonalInformationController;
import com.smartosc.fintech.lms.dto.PersonalInformationDto;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.service.PersonalInformationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class PersonalInformationControllerImpl implements PersonalInformationController {

    private final PersonalInformationService personalInformationService;

    @Override
    @SMFLogger
    public Response<List<PersonalInformationDto>>
        updateLoanPersonalInformation(long id, PersonalInformationDto personalInformationDto) {
        List<PersonalInformationDto> result = personalInformationService.updateLoanPersonalInformation(id, personalInformationDto);
        return Response.ok(result);
    }
}
