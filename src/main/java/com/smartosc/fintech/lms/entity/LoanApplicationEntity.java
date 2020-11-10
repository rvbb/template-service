package com.smartosc.fintech.lms.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;



@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "loan_application")
public class LoanApplicationEntity extends AuditEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "uuid")
  private String uuid;

  @Column(name = "loan_amount")
  private Integer loanAmount;

  @Column(name = "interest_rate")
  private String interestRate;

  @Column(name = "loan_term")
  private String loanTerm;

  @Column(name = "status")
  private Integer status;

  @Column(name = "approve_date")
  private Timestamp approveDate;

  @Column(name = "closed_date")
  private Timestamp closedDate;

  @Column(name = "application_state")
  private String applicationState;

  @Column(name = "application_sub_state")
  private String applicationSubState;

  @Column(name = "accrued_interest")
  private Integer accruedInterest;

  @Column(name = "accrue_late_interest")
  private Integer accrueLateInterest;

  @Column(name = "fee_due")
  private Integer feeDue;

  @Column(name = "fee_paid")
  private Integer feePaid;

  @Column(name = "fixed_days_of_month")
  private Integer fixedDaysOfMonth;

  @Column(name = "interest_application_method")
  private String interestApplicationMethod;

  @Column(name = "interest_calculation_method")
  private String interestCalculationMethod;

  @Column(name = "interest_due")
  private Integer interestDue;

  @Column(name = "interest_paid")
  private Integer interestPaid;

  @Column(name = "payment_method")
  private String paymentMethod;

  @Column(name = "penalty_due")
  private Integer penaltyDue;

  @Column(name = "penalty_paid")
  private Integer penaltyPaid;

  @Column(name = "principal_due")
  private Integer principalDue;

  @Column(name = "principal_paid")
  private Integer principalPaid;

  @Column(name = "loan_product_key")
  private String loanProductKey;

  @Column(name = "repayment_installments")
  private Integer repaymentInstallments;

  @Column(name = "repayment_period_count")
  private Integer repaymentPeriodCount;

  @Column(name = "repayment_period_unit")
  private String repaymentPeriodUnit;

  @Column(name = "repayment_schedule_method")
  private String repaymentScheduleMethod;

  @Column(name = "schedule_due_dates_method")
  private String scheduleDueDatesMethod;

  @Column(name = "tax_rate")
  private Integer taxRate;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", table = "loan_application")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "loan_product_id", referencedColumnName = "id", nullable = false, table = "loan_application")
  private LoanProductEntity loanProduct;

  @OneToMany(mappedBy = "loanApplication")
  private Collection<LoanContactInformationEntity> loanContactInformation;

  @OneToMany(mappedBy = "loanApplication")
  private Collection<LoanCreditScoreEntity> loanCreditScores;

  @OneToMany(mappedBy = "loanApplication")
  private Collection<LoanDisbursementMethodEntity> loanDisbursementMethods;

  @OneToMany(mappedBy = "loanApplication")
  private Collection<LoanJobInformationEntity> loanJobInformation;

  @OneToMany(mappedBy = "loanApplication")
  private Collection<LoanKycInformationEntity> loanKycInformation;

  @OneToMany(mappedBy = "loanApplication")
  private Collection<LoanPersonalInformationEntity> loanPersonalInformation;

  @OneToMany(mappedBy = "loanApplication")
  private Collection<LoanTransactionEntity> loanTransactions;

  @OneToMany(mappedBy = "loanApplication")
  private Collection<RepaymentEntity> repayments;
}
