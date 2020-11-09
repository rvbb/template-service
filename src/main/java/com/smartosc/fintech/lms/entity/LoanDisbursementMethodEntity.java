package com.smartosc.fintech.lms.entity;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "loan_disbursement_method", schema = "lms-service", catalog = "")
public class LoanDisbursementMethodEntity {
    private int id;
    private Integer disbursementMethod;
    private LoanApplicationEntity loanApplication;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "disbursement_method")
    public Integer getDisbursementMethod() {
        return disbursementMethod;
    }

    public void setDisbursementMethod(Integer disbursementMethod) {
        this.disbursementMethod = disbursementMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanDisbursementMethodEntity that = (LoanDisbursementMethodEntity) o;
        return id == that.id &&
                Objects.equals(disbursementMethod, that.disbursementMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, disbursementMethod);
    }

    @ManyToOne
    @JoinColumn(name = "loan_application_id", referencedColumnName = "id", nullable = false, table = "loan_disbursement_method")
    public LoanApplicationEntity getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplicationEntity loanApplication) {
        this.loanApplication = loanApplication;
    }
}
