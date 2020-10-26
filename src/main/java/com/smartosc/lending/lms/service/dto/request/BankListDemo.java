package com.smartosc.lending.lms.service.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class BankListDemo {
  @NotNull
  private String bankName;

  @NotNull
  private int type;

  private Long id;
}
