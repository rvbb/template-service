package com.smartosc.fintech.lms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BankDto extends AuditDto {

  private long id;

  private String name;

  private int type;
}
