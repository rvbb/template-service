package com.smartosc.fintech.lms.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "repayment")
public class RepaymentEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "uuid")
  private String uuid;

  @Column(name = "due_date")
  private Timestamp dueDate;

  @Column(name = "interest_due")
  private Integer interestDue;

  @Column(name = "interest_paid")
  private Integer interestPaid;

  @Column(name = "last_paid_date")
  private Timestamp lastPaidDate;

  @Column(name = "last_penalty_applied_date")
  private Timestamp lastPenaltyAppliedDate;

  @Column(name = "penalty_due")
  private Integer penaltyDue;

  @Column(name = "penalty_paid")
  private Integer penaltyPaid;

  @Column(name = "principal_due")
  private Integer principalDue;

  @Column(name = "principal_paid")
  private Integer principalPaid;

  @Column(name = "fee_due")
  private Integer feeDue;

  @Column(name = "fee_paid")
  private Integer feePaid;

  @Column(name = "repaid_date")
  private Timestamp repaidDate;

  @Column(name = "state")
  private String state;

  @Column(name = "notes")
  private String notes;

  @ManyToOne
  @JoinColumn(name = "user_key", referencedColumnName = "uuid", table = "repayment")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "loan_application_key", referencedColumnName = "uuid", table = "repayment")
  private LoanApplicationEntity loanApplication;

  @OneToMany(mappedBy = "repayment")
  private Collection<RepaymentFeeDetailsEntity> repaymentFeeDetails;
}
