package com.smartosc.fintech.lms.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "loan_kyc_information", schema = "lms-service", catalog = "")
public class LoanKycInformationEntity {
    private Integer id;
    private LoanApplicationEntity loanApplication;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanKycInformationEntity that = (LoanKycInformationEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "loan_application_id", referencedColumnName = "id", table = "loan_kyc_information")
    public LoanApplicationEntity getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplicationEntity loanApplication) {
        this.loanApplication = loanApplication;
    }
}
