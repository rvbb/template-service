package com.smartosc.fintech.lms.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class LoanPersonalInformationDto {

    private long id;

    private String fullName;

    private String phoneNumber;

    private Date dateOfBirth;

    private String emailAddress;

    private String address;

}
