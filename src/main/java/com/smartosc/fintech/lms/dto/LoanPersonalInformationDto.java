package com.smartosc.fintech.lms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class LoanPersonalInformationDto {

    private Integer id;
    private String fullName;
    private String phoneNumber;
    private Date dateOfBirth;

    @NotBlank
    private String emailAddress;
    @NotBlank
    private String address;
}
