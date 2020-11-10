package com.smartosc.fintech.lms.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "loan_personal_information")
public class LoanPersonalInformationEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "date_of_birth")
  private Date dateOfBirth;

  @Column(name = "email_address")
  private String emailAddress;

  @Column(name = "address")
  private String address;

  @ManyToOne
  @JoinColumn(name = "loan_application_id", referencedColumnName = "id", nullable = false, table = "loan_personal_information")
  private LoanApplicationEntity loanApplication;
}
