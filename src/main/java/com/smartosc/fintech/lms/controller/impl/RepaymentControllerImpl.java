package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.controller.RepaymentController;
import com.smartosc.fintech.lms.dto.RepaymentRequestDto;
import com.smartosc.fintech.lms.dto.RepaymentResponseDto;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.service.RepaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class RepaymentControllerImpl implements RepaymentController {

    private RepaymentService repaymentService;

    @Override
    public Response<RepaymentResponseDto> repayLoan(RepaymentRequestDto repaymentRequestDto) {
        return Response.ok(repaymentService.repayLoan(repaymentRequestDto));
    }
}
