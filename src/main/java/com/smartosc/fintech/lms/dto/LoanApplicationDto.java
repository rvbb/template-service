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

  private BigDecimal outstandingBalance;

  private BigDecimal interestAccrued;

  private String interestRate;

  private String loanType;

  private String loanTerm;

  private Integer loanStatus;

  private String expireDate;

  private Collection<LoanContactInformationDto> loanContactInformation;

  private Collection<LoanJobInformationDto> loanJobInformation;

  private Collection<LoanPersonalInformationDto> loanPersonalInformation;

  private PaymentAmountDto paymentAmount;
}
