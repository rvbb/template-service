package com.smartosc.fintech.lms.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "loan_transaction")
public class LoanTransactionEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "uuid")
  private String uuid;

  @Column(name = "amount")
  private Integer amount;

  @Column(name = "balance")
  private Integer balance;

  @Column(name = "creation_date")
  private Timestamp creationDate;

  @Column(name = "entry_date")
  private Timestamp entryDate;

  @Column(name = "fees_amount")
  private Integer feesAmount;

  @Column(name = "interest_amount")
  private Integer interestAmount;

  @Column(name = "interest_rate")
  private Integer interestRate;

  @Column(name = "penalty_amount")
  private Integer penaltyAmount;

  @Column(name = "principal_amount")
  private Integer principalAmount;

  @Column(name = "principal_balance")
  private Integer principalBalance;

  @Column(name = "tax_on_fees_amount")
  private Integer taxOnFeesAmount;

  @Column(name = "tax_on_interest_amount")
  private Integer taxOnInterestAmount;

  @Column(name = "tax_on_penalty_amount")
  private Integer taxOnPenaltyAmount;

  @Column(name = "type")
  private String type;

  @ManyToOne
  @JoinColumn(name = "user_key", referencedColumnName = "uuid", table = "loan_transaction")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "loan_application_key", referencedColumnName = "uuid", table = "loan_transaction")
  private LoanApplicationEntity loanApplication;

  @OneToMany(mappedBy = "loanTransaction")
  private Collection<RepaymentFeeDetailsEntity> repaymentFeeDetails;

}
