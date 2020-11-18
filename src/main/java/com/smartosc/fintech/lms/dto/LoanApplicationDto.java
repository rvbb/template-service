package com.smartosc.fintech.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoanApplicationDto {

  private long id;

  private String uuid;

  private String accountNumber;

  private BigDecimal loanAmount;

  private BigDecimal outstanding_balance;

  private Integer interestAccrued;

  private String interestRate;

  private String loanType;

  private String loanTerm;

  private Integer loanStatus;

  private String expire_date;

  private Collection<LoanContactInformationDto> loanContactInformation;

  private Collection<LoanJobInformationDto> loanJobInformation;

  private Collection<LoanPersonalInformationDto> loanPersonalInformation;

  private Collection<PaymentAmountDto> paymentAmount;
}
