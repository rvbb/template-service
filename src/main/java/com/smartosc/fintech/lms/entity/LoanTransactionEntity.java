package com.smartosc.fintech.lms.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "loan_transaction", schema = "lms-service", catalog = "")
public class LoanTransactionEntity {
    private Integer id;
    private String uuid;
    private Integer amount;
    private Integer balance;
    private LocalDateTime creationDate;
    private LocalDateTime entryDate;
    private Integer feesAmount;
    private Integer interestAmount;
    private Integer interestRate;
    private Integer penaltyAmount;
    private Integer principalAmount;
    private Integer principalBalance;
    private Integer taxOnFeesAmount;
    private Integer taxOnInterestAmount;
    private Integer taxOnPenaltyAmount;
    private String type;
    private UserEntity user;
    private LoanApplicationEntity loanApplication;
    private Collection<RepaymentFeeDetailsEntity> repaymentFeeDetails;

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
    @Column(name = "amount")
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "balance")
    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "creation_date")
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "entry_date")
    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    @Basic
    @Column(name = "fees_amount")
    public Integer getFeesAmount() {
        return feesAmount;
    }

    public void setFeesAmount(Integer feesAmount) {
        this.feesAmount = feesAmount;
    }

    @Basic
    @Column(name = "interest_amount")
    public Integer getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Integer interestAmount) {
        this.interestAmount = interestAmount;
    }

    @Basic
    @Column(name = "interest_rate")
    public Integer getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Integer interestRate) {
        this.interestRate = interestRate;
    }

    @Basic
    @Column(name = "penalty_amount")
    public Integer getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(Integer penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    @Basic
    @Column(name = "principal_amount")
    public Integer getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(Integer principalAmount) {
        this.principalAmount = principalAmount;
    }

    @Basic
    @Column(name = "principal_balance")
    public Integer getPrincipalBalance() {
        return principalBalance;
    }

    public void setPrincipalBalance(Integer principalBalance) {
        this.principalBalance = principalBalance;
    }

    @Basic
    @Column(name = "tax_on_fees_amount")
    public Integer getTaxOnFeesAmount() {
        return taxOnFeesAmount;
    }

    public void setTaxOnFeesAmount(Integer taxOnFeesAmount) {
        this.taxOnFeesAmount = taxOnFeesAmount;
    }

    @Basic
    @Column(name = "tax_on_interest_amount")
    public Integer getTaxOnInterestAmount() {
        return taxOnInterestAmount;
    }

    public void setTaxOnInterestAmount(Integer taxOnInterestAmount) {
        this.taxOnInterestAmount = taxOnInterestAmount;
    }

    @Basic
    @Column(name = "tax_on_penalty_amount")
    public Integer getTaxOnPenaltyAmount() {
        return taxOnPenaltyAmount;
    }

    public void setTaxOnPenaltyAmount(Integer taxOnPenaltyAmount) {
        this.taxOnPenaltyAmount = taxOnPenaltyAmount;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanTransactionEntity that = (LoanTransactionEntity) o;
        return id == that.id &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(entryDate, that.entryDate) &&
                Objects.equals(feesAmount, that.feesAmount) &&
                Objects.equals(interestAmount, that.interestAmount) &&
                Objects.equals(interestRate, that.interestRate) &&
                Objects.equals(penaltyAmount, that.penaltyAmount) &&
                Objects.equals(principalAmount, that.principalAmount) &&
                Objects.equals(principalBalance, that.principalBalance) &&
                Objects.equals(taxOnFeesAmount, that.taxOnFeesAmount) &&
                Objects.equals(taxOnInterestAmount, that.taxOnInterestAmount) &&
                Objects.equals(taxOnPenaltyAmount, that.taxOnPenaltyAmount) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, amount, balance, creationDate, entryDate, feesAmount, interestAmount, interestRate, penaltyAmount, principalAmount, principalBalance, taxOnFeesAmount, taxOnInterestAmount, taxOnPenaltyAmount, type);
    }

    @ManyToOne
    @JoinColumn(name = "user_key", referencedColumnName = "uuid", table = "loan_transaction")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "loan_application_key", referencedColumnName = "uuid", table = "loan_transaction")
    public LoanApplicationEntity getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplicationEntity loanApplication) {
        this.loanApplication = loanApplication;
    }

    @OneToMany(mappedBy = "loanTransaction")
    public Collection<RepaymentFeeDetailsEntity> getRepaymentFeeDetails() {
        return repaymentFeeDetails;
    }

    public void setRepaymentFeeDetails(Collection<RepaymentFeeDetailsEntity> repaymentFeeDetails) {
        this.repaymentFeeDetails = repaymentFeeDetails;
    }
}
