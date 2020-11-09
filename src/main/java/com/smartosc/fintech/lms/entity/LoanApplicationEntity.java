package com.smartosc.fintech.lms.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "loan_application", schema = "lms-service", catalog = "")
public class LoanApplicationEntity {
    private Integer id;
    private String uuid;
    private Integer loanAmount;
    private String interestRate;
    private String loanTerm;
    private Integer status;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime approveDate;
    private LocalDateTime closedDate;
    private String applicationState;
    private String applicationSubState;
    private Integer accruedInterest;
    private Integer accrueLateInterest;
    private Integer feeDue;
    private Integer feePaid;
    private Integer fixedDaysOfMonth;
    private String interestApplicationMethod;
    private String interestCalculationMethod;
    private Integer interestDue;
    private Integer interestPaid;
    private String paymentMethod;
    private Integer penaltyDue;
    private Integer penaltyPaid;
    private Integer principalDue;
    private Integer principalPaid;
    private String loanProductKey;
    private Integer repaymentInstallments;
    private Integer repaymentPeriodCount;
    private String repaymentPeriodUnit;
    private String repaymentScheduleMethod;
    private String scheduleDueDatesMethod;
    private Integer taxRate;
    private UserEntity user;
    private LoanProductEntity loanProduct;
    private Collection<LoanContactInformationEntity> loanContactInformations;
    private Collection<LoanCreditScoreEntity> loanCreditScores;
    private Collection<LoanDisbursementMethodEntity> loanDisbursementMethods;
    private Collection<LoanJobInformationEntity> loanJobInformations;
    private Collection<LoanKycInformationEntity> loanKycInformations;
    private Collection<LoanPersonalInformationEntity> loanPersonalInformations;
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
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "loan_amount")
    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Basic
    @Column(name = "interest_rate")
    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    @Basic
    @Column(name = "loan_term")
    public String getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(String loanTerm) {
        this.loanTerm = loanTerm;
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
    @Column(name = "approve_date")
    public LocalDateTime getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(LocalDateTime approveDate) {
        this.approveDate = approveDate;
    }

    @Basic
    @Column(name = "closed_date")
    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    @Basic
    @Column(name = "application_state")
    public String getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(String applicationState) {
        this.applicationState = applicationState;
    }

    @Basic
    @Column(name = "application_sub_state")
    public String getApplicationSubState() {
        return applicationSubState;
    }

    public void setApplicationSubState(String applicationSubState) {
        this.applicationSubState = applicationSubState;
    }

    @Basic
    @Column(name = "accrued_interest")
    public Integer getAccruedInterest() {
        return accruedInterest;
    }

    public void setAccruedInterest(Integer accruedInterest) {
        this.accruedInterest = accruedInterest;
    }

    @Basic
    @Column(name = "accrue_late_interest")
    public Integer getAccrueLateInterest() {
        return accrueLateInterest;
    }

    public void setAccrueLateInterest(Integer accrueLateInterest) {
        this.accrueLateInterest = accrueLateInterest;
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
    @Column(name = "fixed_days_of_month")
    public Integer getFixedDaysOfMonth() {
        return fixedDaysOfMonth;
    }

    public void setFixedDaysOfMonth(Integer fixedDaysOfMonth) {
        this.fixedDaysOfMonth = fixedDaysOfMonth;
    }

    @Basic
    @Column(name = "interest_application_method")
    public String getInterestApplicationMethod() {
        return interestApplicationMethod;
    }

    public void setInterestApplicationMethod(String interestApplicationMethod) {
        this.interestApplicationMethod = interestApplicationMethod;
    }

    @Basic
    @Column(name = "interest_calculation_method")
    public String getInterestCalculationMethod() {
        return interestCalculationMethod;
    }

    public void setInterestCalculationMethod(String interestCalculationMethod) {
        this.interestCalculationMethod = interestCalculationMethod;
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
    @Column(name = "payment_method")
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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
    @Column(name = "loan_product_key")
    public String getLoanProductKey() {
        return loanProductKey;
    }

    public void setLoanProductKey(String loanProductKey) {
        this.loanProductKey = loanProductKey;
    }

    @Basic
    @Column(name = "repayment_installments")
    public Integer getRepaymentInstallments() {
        return repaymentInstallments;
    }

    public void setRepaymentInstallments(Integer repaymentInstallments) {
        this.repaymentInstallments = repaymentInstallments;
    }

    @Basic
    @Column(name = "repayment_period_count")
    public Integer getRepaymentPeriodCount() {
        return repaymentPeriodCount;
    }

    public void setRepaymentPeriodCount(Integer repaymentPeriodCount) {
        this.repaymentPeriodCount = repaymentPeriodCount;
    }

    @Basic
    @Column(name = "repayment_period_unit")
    public String getRepaymentPeriodUnit() {
        return repaymentPeriodUnit;
    }

    public void setRepaymentPeriodUnit(String repaymentPeriodUnit) {
        this.repaymentPeriodUnit = repaymentPeriodUnit;
    }

    @Basic
    @Column(name = "repayment_schedule_method")
    public String getRepaymentScheduleMethod() {
        return repaymentScheduleMethod;
    }

    public void setRepaymentScheduleMethod(String repaymentScheduleMethod) {
        this.repaymentScheduleMethod = repaymentScheduleMethod;
    }

    @Basic
    @Column(name = "schedule_due_dates_method")
    public String getScheduleDueDatesMethod() {
        return scheduleDueDatesMethod;
    }

    public void setScheduleDueDatesMethod(String scheduleDueDatesMethod) {
        this.scheduleDueDatesMethod = scheduleDueDatesMethod;
    }

    @Basic
    @Column(name = "tax_rate")
    public Integer getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Integer taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanApplicationEntity that = (LoanApplicationEntity) o;
        return id == that.id &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(loanAmount, that.loanAmount) &&
                Objects.equals(interestRate, that.interestRate) &&
                Objects.equals(loanTerm, that.loanTerm) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
                Objects.equals(approveDate, that.approveDate) &&
                Objects.equals(closedDate, that.closedDate) &&
                Objects.equals(applicationState, that.applicationState) &&
                Objects.equals(applicationSubState, that.applicationSubState) &&
                Objects.equals(accruedInterest, that.accruedInterest) &&
                Objects.equals(accrueLateInterest, that.accrueLateInterest) &&
                Objects.equals(feeDue, that.feeDue) &&
                Objects.equals(feePaid, that.feePaid) &&
                Objects.equals(fixedDaysOfMonth, that.fixedDaysOfMonth) &&
                Objects.equals(interestApplicationMethod, that.interestApplicationMethod) &&
                Objects.equals(interestCalculationMethod, that.interestCalculationMethod) &&
                Objects.equals(interestDue, that.interestDue) &&
                Objects.equals(interestPaid, that.interestPaid) &&
                Objects.equals(paymentMethod, that.paymentMethod) &&
                Objects.equals(penaltyDue, that.penaltyDue) &&
                Objects.equals(penaltyPaid, that.penaltyPaid) &&
                Objects.equals(principalDue, that.principalDue) &&
                Objects.equals(principalPaid, that.principalPaid) &&
                Objects.equals(loanProductKey, that.loanProductKey) &&
                Objects.equals(repaymentInstallments, that.repaymentInstallments) &&
                Objects.equals(repaymentPeriodCount, that.repaymentPeriodCount) &&
                Objects.equals(repaymentPeriodUnit, that.repaymentPeriodUnit) &&
                Objects.equals(repaymentScheduleMethod, that.repaymentScheduleMethod) &&
                Objects.equals(scheduleDueDatesMethod, that.scheduleDueDatesMethod) &&
                Objects.equals(taxRate, that.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, loanAmount, interestRate, loanTerm, status, createdDate, lastUpdatedDate, approveDate, closedDate, applicationState, applicationSubState, accruedInterest, accrueLateInterest, feeDue, feePaid, fixedDaysOfMonth, interestApplicationMethod, interestCalculationMethod, interestDue, interestPaid, paymentMethod, penaltyDue, penaltyPaid, principalDue, principalPaid, loanProductKey, repaymentInstallments, repaymentPeriodCount, repaymentPeriodUnit, repaymentScheduleMethod, scheduleDueDatesMethod, taxRate);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", table = "loan_application")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "loan_product_id", referencedColumnName = "id", nullable = false, table = "loan_application")
    public LoanProductEntity getLoanProduct() {
        return loanProduct;
    }

    public void setLoanProduct(LoanProductEntity loanProduct) {
        this.loanProduct = loanProduct;
    }

    @OneToMany(mappedBy = "loanApplication")
    public Collection<LoanContactInformationEntity> getLoanContactInformations() {
        return loanContactInformations;
    }

    public void setLoanContactInformations(Collection<LoanContactInformationEntity> loanContactInformations) {
        this.loanContactInformations = loanContactInformations;
    }

    @OneToMany(mappedBy = "loanApplication")
    public Collection<LoanCreditScoreEntity> getLoanCreditScores() {
        return loanCreditScores;
    }

    public void setLoanCreditScores(Collection<LoanCreditScoreEntity> loanCreditScores) {
        this.loanCreditScores = loanCreditScores;
    }

    @OneToMany(mappedBy = "loanApplication")
    public Collection<LoanDisbursementMethodEntity> getLoanDisbursementMethods() {
        return loanDisbursementMethods;
    }

    public void setLoanDisbursementMethods(Collection<LoanDisbursementMethodEntity> loanDisbursementMethods) {
        this.loanDisbursementMethods = loanDisbursementMethods;
    }

    @OneToMany(mappedBy = "loanApplication")
    public Collection<LoanJobInformationEntity> getLoanJobInformations() {
        return loanJobInformations;
    }

    public void setLoanJobInformations(Collection<LoanJobInformationEntity> loanJobInformations) {
        this.loanJobInformations = loanJobInformations;
    }

    @OneToMany(mappedBy = "loanApplication")
    public Collection<LoanKycInformationEntity> getLoanKycInformations() {
        return loanKycInformations;
    }

    public void setLoanKycInformations(Collection<LoanKycInformationEntity> loanKycInformations) {
        this.loanKycInformations = loanKycInformations;
    }

    @OneToMany(mappedBy = "loanApplication")
    public Collection<LoanPersonalInformationEntity> getLoanPersonalInformations() {
        return loanPersonalInformations;
    }

    public void setLoanPersonalInformations(Collection<LoanPersonalInformationEntity> loanPersonalInformations) {
        this.loanPersonalInformations = loanPersonalInformations;
    }

    @OneToMany(mappedBy = "loanApplication")
    public Collection<LoanTransactionEntity> getLoanTransactions() {
        return loanTransactions;
    }

    public void setLoanTransactions(Collection<LoanTransactionEntity> loanTransactions) {
        this.loanTransactions = loanTransactions;
    }

    @OneToMany(mappedBy = "loanApplication")
    public Collection<RepaymentEntity> getRepayments() {
        return repayments;
    }

    public void setRepayments(Collection<RepaymentEntity> repayments) {
        this.repayments = repayments;
    }
}
