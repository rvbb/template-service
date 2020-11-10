package com.smartosc.fintech.lms.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class RepaymentDto {

  private long id;

  private String uuid;

  private Timestamp dueDate;

  private Integer interestDue;

  private Integer interestPaid;

  private Timestamp lastPaidDate;

  private Timestamp lastPenaltyAppliedDate;

  private Integer penaltyDue;

  private Integer penaltyPaid;

  private Integer principalDue;

  private Integer principalPaid;

  private Integer feeDue;

  private Integer feePaid;

  private Timestamp repaidDate;

  private String state;

  private String notes;
}
