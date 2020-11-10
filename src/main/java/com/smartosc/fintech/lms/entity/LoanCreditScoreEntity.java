package com.smartosc.fintech.lms.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "loan_credit_score")
public class LoanCreditScoreEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "credit_score")
  private Long creditScore;

  @ManyToOne
  @JoinColumn(name = "loan_application_id", referencedColumnName = "id", nullable = false, table = "loan_credit_score")
  private LoanApplicationEntity loanApplication;
}
