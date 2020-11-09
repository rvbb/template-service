package com.smartosc.fintech.lms.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "repayment", schema = "lms-service", catalog = "")
public class RepaymentEntity {
    private Integer id;
    private String uuid;
    private LocalDateTime dueDate;
    private Integer interestDue;
    private Integer interestPaid;
    private LocalDateTime lastPaidDate;
    private LocalDateTime lastPenaltyAppliedDate;
    private Integer penaltyDue;
    private Integer penaltyPaid;
    private Integer principalDue;
    private Integer principalPaid;
    private Integer feeDue;
    private Integer feePaid;
    private LocalDateTime repaidDate;
    private String state;
    private String notes;
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
    @Column(name = "due_date")
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Basic
    @Column(name = "interest_due")
    public Integer getInterestDue() {
        return interestDue;
    }

    public void setInterestDue(Integer interestDue) {
        this.interestDue = interestDue;
    }

    @Basic
    @Column(name = "interest_paid")
    public Integer getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(Integer interestPaid) {
        this.interestPaid = interestPaid;
    }

    @Basic
    @Column(name = "last_paid_date")
    public LocalDateTime getLastPaidDate() {
        return lastPaidDate;
    }

    public void setLastPaidDate(LocalDateTime lastPaidDate) {
        this.lastPaidDate = lastPaidDate;
    }

    @Basic
    @Column(name = "last_penalty_applied_date")
    public LocalDateTime getLastPenaltyAppliedDate() {
        return lastPenaltyAppliedDate;
    }

    public void setLastPenaltyAppliedDate(LocalDateTime lastPenaltyAppliedDate) {
        this.lastPenaltyAppliedDate = lastPenaltyAppliedDate;
    }

    @Basic
    @Column(name = "penalty_due")
    public Integer getPenaltyDue() {
        return penaltyDue;
    }

    public void setPenaltyDue(Integer penaltyDue) {
        this.penaltyDue = penaltyDue;
    }

    @Basic
    @Column(name = "penalty_paid")
    public Integer getPenaltyPaid() {
        return penaltyPaid;
    }

    public void setPenaltyPaid(Integer penaltyPaid) {
        this.penaltyPaid = penaltyPaid;
    }

    @Basic
    @Column(name = "principal_due")
    public Integer getPrincipalDue() {
        return principalDue;
    }

    public void setPrincipalDue(Integer principalDue) {
        this.principalDue = principalDue;
    }

    @Basic
    @Column(name = "principal_paid")
    public Integer getPrincipalPaid() {
        return principalPaid;
    }

    public void setPrincipalPaid(Integer principalPaid) {
        this.principalPaid = principalPaid;
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
    @Column(name = "repaid_date")
    public LocalDateTime getRepaidDate() {
        return repaidDate;
    }

    public void setRepaidDate(LocalDateTime repaidDate) {
        this.repaidDate = repaidDate;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepaymentEntity that = (RepaymentEntity) o;
        return id == that.id &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(interestDue, that.interestDue) &&
                Objects.equals(interestPaid, that.interestPaid) &&
                Objects.equals(lastPaidDate, that.lastPaidDate) &&
                Objects.equals(lastPenaltyAppliedDate, that.lastPenaltyAppliedDate) &&
                Objects.equals(penaltyDue, that.penaltyDue) &&
                Objects.equals(penaltyPaid, that.penaltyPaid) &&
                Objects.equals(principalDue, that.principalDue) &&
                Objects.equals(principalPaid, that.principalPaid) &&
                Objects.equals(feeDue, that.feeDue) &&
                Objects.equals(feePaid, that.feePaid) &&
                Objects.equals(repaidDate, that.repaidDate) &&
                Objects.equals(state, that.state) &&
                Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, dueDate, interestDue, interestPaid, lastPaidDate, lastPenaltyAppliedDate, penaltyDue, penaltyPaid, principalDue, principalPaid, feeDue, feePaid, repaidDate, state, notes);
    }

    @ManyToOne
    @JoinColumn(name = "user_key", referencedColumnName = "uuid", table = "repayment")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "loan_application_key", referencedColumnName = "uuid", table = "repayment")
    public LoanApplicationEntity getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplicationEntity loanApplication) {
        this.loanApplication = loanApplication;
    }

    @OneToMany(mappedBy = "repayment")
    public Collection<RepaymentFeeDetailsEntity> getRepaymentFeeDetails() {
        return repaymentFeeDetails;
    }

    public void setRepaymentFeeDetails(Collection<RepaymentFeeDetailsEntity> repaymentFeeDetails) {
        this.repaymentFeeDetails = repaymentFeeDetails;
    }
}
