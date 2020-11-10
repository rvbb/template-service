package com.smartosc.fintech.lms.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AuditDto {

  private Timestamp createdDate;

  private Timestamp lastModifiedDate;
}
