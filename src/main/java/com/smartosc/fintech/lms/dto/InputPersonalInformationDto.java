package com.smartosc.fintech.lms.dto;

import com.smartosc.fintech.lms.validator.EmailConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class InputPersonalInformationDto {

    @EmailConstraint
    private String emailAddress;

    @Size(max = 100)
    @NotBlank
    private String address;
}
