package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.dto.LoanTransactionDto;
import com.smartosc.fintech.lms.dto.LoanTransactionRequest;
import com.smartosc.fintech.lms.dto.PagingResponse;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.repository.LoanTransactionRepository;
import com.smartosc.fintech.lms.service.LoanTransactionService;
import com.smartosc.fintech.lms.service.mapper.LoanTransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class LoanTransactionServiceImpl implements LoanTransactionService {
    private final LoanTransactionRepository loanTransactionRepository;

    @Override
    public LoanTransactionDto getLoanTransaction(String uuid) {
        LoanTransactionEntity transaction = loanTransactionRepository.findByUuid(uuid)
                                                .orElseThrow(() ->new EntityNotFoundException("Loan transaction not found"));

        return LoanTransactionMapper.INSTANCE.mapToDto(transaction);
    }

    @Override
    public PagingResponse<LoanTransactionDto> getListLoanTransaction(LoanTransactionRequest request) {
        return new PagingResponse<>();
    }
}
