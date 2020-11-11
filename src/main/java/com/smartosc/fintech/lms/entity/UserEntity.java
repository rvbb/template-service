package com.smartosc.fintech.lms.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user")
public class UserEntity extends AuditEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @Column(name = "last_logged_in_date")
    private Timestamp lastLoggedInDate;

    @Column(name = "uuid")
    private String uuid;

    @OneToMany(mappedBy = "user")
    private Collection<LoanApplicationEntity> loanApplications;

    @OneToMany(mappedBy = "user")
    private Collection<LoanTransactionEntity> loanTransactions;

    @OneToMany(mappedBy = "user")
    private Collection<RepaymentEntity> repayments;
}
