package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.dto.BankDto;
import com.smartosc.fintech.lms.entity.BankEntity;
import com.smartosc.fintech.lms.service.mapper.BankMapper;
import com.smartosc.fintech.lms.repository.BankRepository;
import com.smartosc.fintech.lms.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class BankServiceImpl implements BankService {

  private final BankRepository bankRepository;

  @Autowired
  public BankServiceImpl(BankRepository bankRepository) {
    this.bankRepository = bankRepository;
  }

  @Override
  public Page<BankDto> get(Pageable pageable) {
    Page<BankEntity> bankEntities = bankRepository.findAll(pageable);
    return bankEntities.map(BankMapper.INSTANCE::bankEntityToBankDto);
  }

  @Override
  public BankDto get(long id) {
    Optional<BankEntity> bankEntityOptional = bankRepository.findById(id);
    if (!bankEntityOptional.isPresent()) {
      throw new EntityNotFoundException();
    }

    return BankMapper.INSTANCE.bankEntityToBankDto(bankEntityOptional.get());
  }

  @Override
  public BankDto create(BankDto bankDto) {
    BankEntity bankEntity = BankMapper.INSTANCE.bankDtoToBankEntity(bankDto);
    bankEntity = bankRepository.save(bankEntity);

    return BankMapper.INSTANCE.bankEntityToBankDto(bankEntity);
  }

  @Override
  public BankDto update(long id, BankDto bankDto) {
    if (!bankRepository.existsById(id)) {
      throw new EntityNotFoundException();
    }

    BankEntity bankEntity = BankMapper.INSTANCE.bankDtoToBankEntity(bankDto);
    bankEntity.setId(id);
    bankEntity = bankRepository.save(bankEntity);

    return BankMapper.INSTANCE.bankEntityToBankDto(bankEntity);
  }

  @Override
  public void delete(long id) {
    if (!bankRepository.existsById(id)) {
      throw new EntityNotFoundException();
    }

    bankRepository.deleteById(id);
  }
}
