package com.smartosc.fintech.lms.dto;

import com.smartosc.fintech.lms.validator.EmailConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@Getter
@Setter
public class PersonalInformationDto {

    private Integer id;
    private String fullName;
    private String phoneNumber;
    private Date dateOfBirth;

    @EmailConstraint
    private String emailAddress;

    @Size(max = 100)
    @NotBlank
    private String address;
}
