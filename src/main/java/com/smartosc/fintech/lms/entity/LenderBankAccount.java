package com.smartosc.fintech.lms.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lender_bank_account")
public class LenderBankAccount {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "account")
    private String account;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "bank_name")
    private String bankName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_application_key", referencedColumnName = "uuid", table = "lender_bank_account")
    private LoanApplicationEntity loanApplication;

}
