package com.smartosc.fintech.lms.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "loan_credit_score", schema = "lms-service", catalog = "")
public class LoanCreditScoreEntity {
    private Integer id;
    private Long creditScore;
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
    @Column(name = "credit_score")
    public Long getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Long creditScore) {
        this.creditScore = creditScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanCreditScoreEntity that = (LoanCreditScoreEntity) o;
        return id == that.id &&
                Objects.equals(creditScore, that.creditScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creditScore);
    }

    @ManyToOne
    @JoinColumn(name = "loan_application_id", referencedColumnName = "id", nullable = false, table = "loan_credit_score")
    public LoanApplicationEntity getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplicationEntity loanApplication) {
        this.loanApplication = loanApplication;
    }
}
