package com.smartosc.fintech.lms.dto;

import lombok.Data;

import java.sql.Date;


@Data
public class LoanContactInformationDto {

    private long id;

    private Integer refType;

    private String refFullName;

    private String refPhoneNumber;

    private Date refDob;

    private String refEmail;

    private String refAddress;
}
