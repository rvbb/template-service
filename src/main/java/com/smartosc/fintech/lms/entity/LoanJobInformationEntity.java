package com.smartosc.fintech.lms.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "loan_job_information")
public class LoanJobInformationEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "pre_tax_income")
  private Long preTaxIncome;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "company_address")
  private String companyAddress;

  @ManyToOne
  @JoinColumn(name = "loan_application_id", referencedColumnName = "id", nullable = false, table = "loan_job_information")
  private LoanApplicationEntity loanApplication;
}
