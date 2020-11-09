package com.smartosc.fintech.lms.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "repayment_fee_details", schema = "lms-service", catalog = "")
public class RepaymentFeeDetailsEntity {
    private int id;
    private String uuid;
    private Integer feeDue;
    private Integer feePaid;
    private Integer taxOnFeeDue;
    private Integer taxOnFeePaid;
    private RepaymentEntity repayment;
    private LoanTransactionEntity loanTransaction;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "fee_due")
    public Integer getFeeDue() {
        return feeDue;
    }

    public void setFeeDue(Integer feeDue) {
        this.feeDue = feeDue;
    }

    @Basic
    @Column(name = "fee_paid")
    public Integer getFeePaid() {
        return feePaid;
    }

    public void setFeePaid(Integer feePaid) {
        this.feePaid = feePaid;
    }

    @Basic
    @Column(name = "tax_on_fee_due")
    public Integer getTaxOnFeeDue() {
        return taxOnFeeDue;
    }

    public void setTaxOnFeeDue(Integer taxOnFeeDue) {
        this.taxOnFeeDue = taxOnFeeDue;
    }

    @Basic
    @Column(name = "tax_on_fee_paid")
    public Integer getTaxOnFeePaid() {
        return taxOnFeePaid;
    }

    public void setTaxOnFeePaid(Integer taxOnFeePaid) {
        this.taxOnFeePaid = taxOnFeePaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepaymentFeeDetailsEntity that = (RepaymentFeeDetailsEntity) o;
        return id == that.id &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(feeDue, that.feeDue) &&
                Objects.equals(feePaid, that.feePaid) &&
                Objects.equals(taxOnFeeDue, that.taxOnFeeDue) &&
                Objects.equals(taxOnFeePaid, that.taxOnFeePaid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, feeDue, feePaid, taxOnFeeDue, taxOnFeePaid);
    }

    @ManyToOne
    @JoinColumn(name = "repayment_key", referencedColumnName = "uuid", table = "repayment_fee_details")
    public RepaymentEntity getRepayment() {
        return repayment;
    }

    public void setRepayment(RepaymentEntity repayment) {
        this.repayment = repayment;
    }

    @ManyToOne
    @JoinColumn(name = "loan_transaction_key", referencedColumnName = "uuid", table = "repayment_fee_details")
    public LoanTransactionEntity getLoanTransaction() {
        return loanTransaction;
    }

    public void setLoanTransaction(LoanTransactionEntity loanTransaction) {
        this.loanTransaction = loanTransaction;
    }
}
