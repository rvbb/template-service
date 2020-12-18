package com.rvbb.api.template.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class FinanceInfoInput {

    @Size(max = 15, min = 1)
    @NotBlank
    private String preTaxIncome;

    @Size(max = 150, min = 15)
    @NotBlank
    private String companyName;

    @Size(max = 150, min = 15)
    @NotBlank
    private String companyAddress;

    @Size(max = 15, min = 1)
    @NotBlank
    private String expense;

}
