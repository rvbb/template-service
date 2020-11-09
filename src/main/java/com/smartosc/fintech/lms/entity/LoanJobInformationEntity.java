package com.smartosc.fintech.lms.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "loan_job_information", schema = "lms-service", catalog = "")
public class LoanJobInformationEntity {
    private int id;
    private Long preTaxIncome;
    private String companyName;
    private String companyAddress;
    private LoanApplicationEntity loanApplication;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pre_tax_income")
    public Long getPreTaxIncome() {
        return preTaxIncome;
    }

    public void setPreTaxIncome(Long preTaxIncome) {
        this.preTaxIncome = preTaxIncome;
    }

    @Basic
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "company_address")
    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanJobInformationEntity that = (LoanJobInformationEntity) o;
        return id == that.id &&
                Objects.equals(preTaxIncome, that.preTaxIncome) &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(companyAddress, that.companyAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, preTaxIncome, companyName, companyAddress);
    }

    @ManyToOne
    @JoinColumn(name = "loan_application_id", referencedColumnName = "id", nullable = false, table = "loan_job_information")
    public LoanApplicationEntity getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplicationEntity loanApplication) {
        this.loanApplication = loanApplication;
    }
}
