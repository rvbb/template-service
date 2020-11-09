package com.smartosc.fintech.lms.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Table(name = "loan_contact_information", schema = "lms-service", catalog = "")
public class LoanContactInformationEntity {
    private int id;
    private Integer refType;
    private String refFullName;
    private String refPhoneNumber;
    private Date refDob;
    private String refEmail;
    private String refAddress;
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
    @Column(name = "ref_type")
    public Integer getRefType() {
        return refType;
    }

    public void setRefType(Integer refType) {
        this.refType = refType;
    }

    @Basic
    @Column(name = "ref_full_name")
    public String getRefFullName() {
        return refFullName;
    }

    public void setRefFullName(String refFullName) {
        this.refFullName = refFullName;
    }

    @Basic
    @Column(name = "ref_phone_number")
    public String getRefPhoneNumber() {
        return refPhoneNumber;
    }

    public void setRefPhoneNumber(String refPhoneNumber) {
        this.refPhoneNumber = refPhoneNumber;
    }

    @Basic
    @Column(name = "ref_dob")
    public Date getRefDob() {
        return refDob;
    }

    public void setRefDob(Date refDob) {
        this.refDob = refDob;
    }

    @Basic
    @Column(name = "ref_email")
    public String getRefEmail() {
        return refEmail;
    }

    public void setRefEmail(String refEmail) {
        this.refEmail = refEmail;
    }

    @Basic
    @Column(name = "ref_address")
    public String getRefAddress() {
        return refAddress;
    }

    public void setRefAddress(String refAddress) {
        this.refAddress = refAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanContactInformationEntity that = (LoanContactInformationEntity) o;
        return id == that.id &&
                Objects.equals(refType, that.refType) &&
                Objects.equals(refFullName, that.refFullName) &&
                Objects.equals(refPhoneNumber, that.refPhoneNumber) &&
                Objects.equals(refDob, that.refDob) &&
                Objects.equals(refEmail, that.refEmail) &&
                Objects.equals(refAddress, that.refAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, refType, refFullName, refPhoneNumber, refDob, refEmail, refAddress);
    }

    @ManyToOne
    @JoinColumn(name = "loan_application_id", referencedColumnName = "id", nullable = false, table = "loan_contact_information")
    public LoanApplicationEntity getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplicationEntity loanApplication) {
        this.loanApplication = loanApplication;
    }
}
