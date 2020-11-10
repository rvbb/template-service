package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.RepaymentDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.RepaymentRepository;
import com.smartosc.fintech.lms.service.RepaymentService;
import com.smartosc.fintech.lms.service.mapper.RepaymentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RepaymentServiceImpl implements RepaymentService {

  private final LoanApplicationRepository loanApplicationRepository;

  private final RepaymentRepository repaymentRepository;

  @Autowired
  public RepaymentServiceImpl(LoanApplicationRepository loanApplicationRepository, RepaymentRepository repaymentRepository) {
    this.loanApplicationRepository = loanApplicationRepository;
    this.repaymentRepository = repaymentRepository;
  }

  @Override
  public List<RepaymentDto> calculate(LoanApplicationDto loanApplicationDto) {
    return calculate(loanApplicationDto.getId());
  }

  @Override
  public List<RepaymentDto> calculate(long loanApplicationId) {
    Optional<LoanApplicationEntity> optional = loanApplicationRepository.findById(loanApplicationId);

    LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(EntityNotFoundException::new);
    log.info(loanApplicationEntity.toString());

    List<RepaymentEntity> repaymentEntities = calculate(loanApplicationEntity);
    repaymentEntities = repaymentRepository.saveAll(repaymentEntities);

    return repaymentEntities.stream().map(RepaymentMapper.INSTANCE::entityToDto).collect(Collectors.toList());
  }

  private List<RepaymentEntity> calculate(LoanApplicationEntity loanApplicationEntity) {
    // TODO: calculate repayment
    return Collections.emptyList();
  }
}
