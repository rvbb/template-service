package com.smartosc.fintech.lms.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "repayment_fee_details")
public class RepaymentFeeDetailsEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "uuid")
  private String uuid;

  @Column(name = "fee_due")
  private Integer feeDue;

  @Column(name = "fee_paid")
  private Integer feePaid;

  @Column(name = "tax_on_fee_due")
  private Integer taxOnFeeDue;

  @Column(name = "tax_on_fee_paid")
  private Integer taxOnFeePaid;

  @ManyToOne
  @JoinColumn(name = "repayment_key", referencedColumnName = "uuid", table = "repayment_fee_details")
  private RepaymentEntity repayment;

  @ManyToOne
  @JoinColumn(name = "loan_transaction_key", referencedColumnName = "uuid", table = "repayment_fee_details")
  private LoanTransactionEntity loanTransaction;
}
