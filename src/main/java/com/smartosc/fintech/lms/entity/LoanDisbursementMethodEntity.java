package com.smartosc.fintech.lms.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "loan_disbursement_method")
public class LoanDisbursementMethodEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "disbursement_method")
    private Integer disbursementMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_application_id", referencedColumnName = "id", nullable = false, table = "loan_disbursement_method")
    private LoanApplicationEntity loanApplication;
}
