package com.smartosc.fintech.lms.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "lms-service", catalog = "")
public class UserEntity {
    private Integer id;
    private String email;
    private String password;
    private Integer status;
    private LocalDateTime lastPasswordResetDate;
    private LocalDateTime lastLoggedInDate;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private String uuid;
    private Collection<LoanApplicationEntity> loanApplications;
    private Collection<LoanTransactionEntity> loanTransactions;
    private Collection<RepaymentEntity> repayments;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "last_password_reset_date")
    public LocalDateTime getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(LocalDateTime lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @Basic
    @Column(name = "last_logged_in_date")
    public LocalDateTime getLastLoggedInDate() {
        return lastLoggedInDate;
    }

    public void setLastLoggedInDate(LocalDateTime lastLoggedInDate) {
        this.lastLoggedInDate = lastLoggedInDate;
    }

    @Basic
    @Column(name = "created_date")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "last_updated_date")
    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Basic
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(status, that.status) &&
                Objects.equals(lastPasswordResetDate, that.lastPasswordResetDate) &&
                Objects.equals(lastLoggedInDate, that.lastLoggedInDate) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
                Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, status, lastPasswordResetDate, lastLoggedInDate, createdDate, lastUpdatedDate, uuid);
    }

    @OneToMany(mappedBy = "user")
    public Collection<LoanApplicationEntity> getLoanApplications() {
        return loanApplications;
    }

    public void setLoanApplications(Collection<LoanApplicationEntity> loanApplications) {
        this.loanApplications = loanApplications;
    }

    @OneToMany(mappedBy = "user")
    public Collection<LoanTransactionEntity> getLoanTransactions() {
        return loanTransactions;
    }

    public void setLoanTransactions(Collection<LoanTransactionEntity> loanTransactions) {
        this.loanTransactions = loanTransactions;
    }

    @OneToMany(mappedBy = "user")
    public Collection<RepaymentEntity> getRepayments() {
        return repayments;
    }

    public void setRepayments(Collection<RepaymentEntity> repayments) {
        this.repayments = repayments;
    }
}
