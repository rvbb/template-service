package com.smartosc.fintech.lms.test.service.impl;

import com.smartosc.fintech.lms.dto.RepaymentDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.service.RepaymentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RepaymentServiceImplTests {

    @InjectMocks
    private RepaymentService repaymentService;

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    private LoanApplicationEntity loanApplicationEntity;

    private List<RepaymentDto> excepted;

    @Before
    public void init() {
        this.loanApplicationEntity = createLoanApplicationEntity();
        this.excepted = createRepayments();
    }

    private LoanApplicationEntity createLoanApplicationEntity() {
        LoanApplicationEntity loanApplicationEntity = new LoanApplicationEntity();
        loanApplicationEntity.setApproveDate(Timestamp.valueOf("1604213825"));
        loanApplicationEntity.setPrincipalDue(BigDecimal.valueOf(100_000_000));
        loanApplicationEntity.setInterestDue(BigDecimal.valueOf(1_000_000));
        return loanApplicationEntity;
    }

    private List<RepaymentDto> createRepayments() {
        return null;
    }

    @Test
    public void testCalculate_whenInputLoanApplicationUuid_thenReturnRepayments() {
        Mockito.when(loanApplicationRepository.findLoanApplicationEntityByUuid("uuid"))
                .thenReturn(Optional.of(loanApplicationEntity));

        List<RepaymentDto> actual = repaymentService.calculate("uuid");

        Assertions.assertEquals(excepted, actual);
    }

}
