package com.smartosc.lending.lms.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Use this if data response to client is difference entity
 **/
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankListDemoResponse {
    private String nameOfbank;
    private int type;
    /*private String created;//eg client no need "created" data*/
}
