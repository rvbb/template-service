package com.smartosc.fintech.lms.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "loan_product", schema = "lms-service", catalog = "")
public class LoanProductEntity {
    private Integer id;
    private String name;
    private String description;
    private Long minAmount;
    private Long maxAmount;
    private String interestRate;
    private Integer type;
    private String interestAccruedAccountingMethod;
    private String interestApplicationMethod;
    private String interestCalculationMethod;
    private String interestRateKey;
    private Integer fixedDaysOfMonth;
    private String penaltyCalculationMethod;
    private String paymentmethod;
    private String prepaymentAcceptance;
    private String prepaymentRecalculationMethod;
    private String principalPaidInstallmentStatus;
    private String principalPaymentKey;
    private String repaymentAllocationOrder;
    private Integer repaymentPeriodCount;
    private String repaymentPeriodUnit;
    private String repaymentScheduleMethod;
    private String taxCalculationMethod;
    private Integer status;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private Integer daysInYear;
    private String loanPenaltyRate;
    private Collection<LoanApplicationEntity> loanApplications;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "min_amount")
    public Long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Long minAmount) {
        this.minAmount = minAmount;
    }

    @Basic
    @Column(name = "max_amount")
    public Long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
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
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "interest_accrued_accounting_method")
    public String getInterestAccruedAccountingMethod() {
        return interestAccruedAccountingMethod;
    }

    public void setInterestAccruedAccountingMethod(String interestAccruedAccountingMethod) {
        this.interestAccruedAccountingMethod = interestAccruedAccountingMethod;
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
    @Column(name = "interest_rate_key")
    public String getInterestRateKey() {
        return interestRateKey;
    }

    public void setInterestRateKey(String interestRateKey) {
        this.interestRateKey = interestRateKey;
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
    @Column(name = "penalty_calculation_method")
    public String getPenaltyCalculationMethod() {
        return penaltyCalculationMethod;
    }

    public void setPenaltyCalculationMethod(String penaltyCalculationMethod) {
        this.penaltyCalculationMethod = penaltyCalculationMethod;
    }

    @Basic
    @Column(name = "paymentmethod")
    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    @Basic
    @Column(name = "prepayment_acceptance")
    public String getPrepaymentAcceptance() {
        return prepaymentAcceptance;
    }

    public void setPrepaymentAcceptance(String prepaymentAcceptance) {
        this.prepaymentAcceptance = prepaymentAcceptance;
    }

    @Basic
    @Column(name = "prepayment_recalculation_method")
    public String getPrepaymentRecalculationMethod() {
        return prepaymentRecalculationMethod;
    }

    public void setPrepaymentRecalculationMethod(String prepaymentRecalculationMethod) {
        this.prepaymentRecalculationMethod = prepaymentRecalculationMethod;
    }

    @Basic
    @Column(name = "principal_paid_installment_status")
    public String getPrincipalPaidInstallmentStatus() {
        return principalPaidInstallmentStatus;
    }

    public void setPrincipalPaidInstallmentStatus(String principalPaidInstallmentStatus) {
        this.principalPaidInstallmentStatus = principalPaidInstallmentStatus;
    }

    @Basic
    @Column(name = "principal_payment_key")
    public String getPrincipalPaymentKey() {
        return principalPaymentKey;
    }

    public void setPrincipalPaymentKey(String principalPaymentKey) {
        this.principalPaymentKey = principalPaymentKey;
    }

    @Basic
    @Column(name = "repayment_allocation_order")
    public String getRepaymentAllocationOrder() {
        return repaymentAllocationOrder;
    }

    public void setRepaymentAllocationOrder(String repaymentAllocationOrder) {
        this.repaymentAllocationOrder = repaymentAllocationOrder;
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
    @Column(name = "tax_calculation_method")
    public String getTaxCalculationMethod() {
        return taxCalculationMethod;
    }

    public void setTaxCalculationMethod(String taxCalculationMethod) {
        this.taxCalculationMethod = taxCalculationMethod;
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
    @Column(name = "days_in_year")
    public Integer getDaysInYear() {
        return daysInYear;
    }

    public void setDaysInYear(Integer daysInYear) {
        this.daysInYear = daysInYear;
    }

    @Basic
    @Column(name = "loan_penalty_rate")
    public String getLoanPenaltyRate() {
        return loanPenaltyRate;
    }

    public void setLoanPenaltyRate(String loanPenaltyRate) {
        this.loanPenaltyRate = loanPenaltyRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanProductEntity that = (LoanProductEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(minAmount, that.minAmount) &&
                Objects.equals(maxAmount, that.maxAmount) &&
                Objects.equals(interestRate, that.interestRate) &&
                Objects.equals(type, that.type) &&
                Objects.equals(interestAccruedAccountingMethod, that.interestAccruedAccountingMethod) &&
                Objects.equals(interestApplicationMethod, that.interestApplicationMethod) &&
                Objects.equals(interestCalculationMethod, that.interestCalculationMethod) &&
                Objects.equals(interestRateKey, that.interestRateKey) &&
                Objects.equals(fixedDaysOfMonth, that.fixedDaysOfMonth) &&
                Objects.equals(penaltyCalculationMethod, that.penaltyCalculationMethod) &&
                Objects.equals(paymentmethod, that.paymentmethod) &&
                Objects.equals(prepaymentAcceptance, that.prepaymentAcceptance) &&
                Objects.equals(prepaymentRecalculationMethod, that.prepaymentRecalculationMethod) &&
                Objects.equals(principalPaidInstallmentStatus, that.principalPaidInstallmentStatus) &&
                Objects.equals(principalPaymentKey, that.principalPaymentKey) &&
                Objects.equals(repaymentAllocationOrder, that.repaymentAllocationOrder) &&
                Objects.equals(repaymentPeriodCount, that.repaymentPeriodCount) &&
                Objects.equals(repaymentPeriodUnit, that.repaymentPeriodUnit) &&
                Objects.equals(repaymentScheduleMethod, that.repaymentScheduleMethod) &&
                Objects.equals(taxCalculationMethod, that.taxCalculationMethod) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
                Objects.equals(daysInYear, that.daysInYear) &&
                Objects.equals(loanPenaltyRate, that.loanPenaltyRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, minAmount, maxAmount, interestRate, type, interestAccruedAccountingMethod, interestApplicationMethod, interestCalculationMethod, interestRateKey, fixedDaysOfMonth, penaltyCalculationMethod, paymentmethod, prepaymentAcceptance, prepaymentRecalculationMethod, principalPaidInstallmentStatus, principalPaymentKey, repaymentAllocationOrder, repaymentPeriodCount, repaymentPeriodUnit, repaymentScheduleMethod, taxCalculationMethod, status, createdDate, lastUpdatedDate, daysInYear, loanPenaltyRate);
    }

    @OneToMany(mappedBy = "loanProduct")
    public Collection<LoanApplicationEntity> getLoanApplications() {
        return loanApplications;
    }

    public void setLoanApplications(Collection<LoanApplicationEntity> loanApplications) {
        this.loanApplications = loanApplications;
    }
}
