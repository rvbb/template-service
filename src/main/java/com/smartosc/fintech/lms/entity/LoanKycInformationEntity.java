package com.smartosc.fintech.lms.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "loan_kyc_information")
public class LoanKycInformationEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_application_id", referencedColumnName = "id", table = "loan_kyc_information")
    private LoanApplicationEntity loanApplication;
}
