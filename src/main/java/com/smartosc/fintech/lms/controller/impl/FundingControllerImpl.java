package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.controller.FundingController;
import com.smartosc.fintech.lms.dto.FundingRequest;
import com.smartosc.fintech.lms.service.FundingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FundingControllerImpl implements FundingController {
    private final FundingService fundingService;

    @SMFLogger
    @Override
    public void makeFunding(FundingRequest request) {
        fundingService.makeFunding(request);
    }
}
