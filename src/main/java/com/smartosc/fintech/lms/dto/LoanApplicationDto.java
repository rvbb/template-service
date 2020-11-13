package com.smartosc.fintech.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoanApplicationDto {

  private long id;

  private String uuid;

  private Integer loanAmount;

  private String interestRate;

  private String loanTerm;

  private Integer status;

  private Timestamp approveDate;

  private Timestamp closedDate;

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

  private UserDto user;

  private LoanProductDto loanProduct;

  private Collection<LoanContactInformationDto> loanContactInformation;

  private Collection<LoanCreditScoreDto> loanCreditScores;

  private Collection<LoanDisbursementMethodDto> loanDisbursementMethods;

  private Collection<LoanJobInformationDto> loanJobInformation;

  private Collection<LoanKycInformationDto> loanKycInformation;

  private Collection<LoanPersonalInformationDto> loanPersonalInformation;

  private Collection<LoanTransactionDto> loanTransactions;

  private Collection<RepaymentDto> repayments;
}
