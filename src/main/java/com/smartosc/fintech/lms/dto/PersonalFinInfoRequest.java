package com.smartosc.fintech.lms.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PersonalFinInfoRequest {

    @Size(max = 15)
    @NotBlank
    private String preTaxIncome;

    @Size(max = 150, min = 15)
    @NotBlank
    private String companyName;

    @Size(max = 150, min = 15)
    @NotBlank
    private String companyAddress;

    @Size(max = 15)
    @NotBlank
    private String expense;
}
