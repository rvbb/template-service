package com.rvbb.api.template.service.impl;

import com.rvbb.api.template.common.util.LogIt;
import com.rvbb.api.template.dto.FinanceInfoInput;
import com.rvbb.api.template.dto.FinanceInfoRes;
import com.rvbb.api.template.entity.FinanceInfoEntity;
import com.rvbb.api.template.repository.FinanceInfoRepository;
import com.rvbb.api.template.repository.FinanceInfoXpanseRepository;
import com.rvbb.api.template.service.IFinanceInfoService;
import com.rvbb.api.template.service.mapper.FinanceInfoMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor
public class FinanceInfoService implements IFinanceInfoService {

    private final FinanceInfoRepository finInfoRepository;
    private final FinanceInfoXpanseRepository financeInfoXpanseRepository;

    @Override
    @LogIt
    public FinanceInfoRes getLast(String uuid) {
        FinanceInfoEntity entity = finInfoRepository.getLastFinInfoByUuid(uuid);
        if (ObjectUtils.isEmpty(entity)) {
            throw new EntityNotFoundException("Not found financial information uuid : " + uuid);
        }
        return FinanceInfoMapper.INSTANCE.toDto(entity);
    }

    @Override
    @LogIt
    public FinanceInfoRes get(String uuid) {
        Optional<FinanceInfoEntity> optional = finInfoRepository.findByUuid(uuid);
        FinanceInfoEntity financeInfo = optional.orElseThrow(
                () -> new EntityNotFoundException("Not found finance information with uuid : " + uuid));
        return FinanceInfoMapper.INSTANCE.toDto(financeInfo);
    }

    @Override
    @LogIt
    public FinanceInfoRes create(FinanceInfoInput request) {
        double expense = Math.floor(Double.valueOf(request.getExpense()) * 100) / 100;
        double preTaxIncome = Math.floor(Double.valueOf(request.getPreTaxIncome()) * 100) / 100;
        FinanceInfoEntity newEntity = FinanceInfoEntity.builder()
                .preTaxIncome(BigDecimal.valueOf(preTaxIncome))
                .companyAddress(request.getCompanyAddress())
                .companyName(request.getCompanyName())
                .expense(BigDecimal.valueOf(expense))
                .lastUpdate(new Date())
                .build();
        finInfoRepository.save(newEntity);
        return FinanceInfoMapper.INSTANCE.toDto(newEntity);
    }

    @Override
    @LogIt
    public FinanceInfoRes update(String uuid, FinanceInfoInput request) {
        double expense = Math.floor(Double.valueOf(request.getExpense()) * 100) / 100;
        double preTaxIncome = Math.floor(Double.valueOf(request.getPreTaxIncome()) * 100) / 100;
        FinanceInfoEntity oldEntity = finInfoRepository.getLastFinInfoByUuid(uuid);
        oldEntity.setPreTaxIncome(BigDecimal.valueOf(preTaxIncome));
        oldEntity.setCompanyAddress(request.getCompanyAddress());
        oldEntity.setCompanyName(request.getCompanyName());
        oldEntity.setExpense(BigDecimal.valueOf(expense));
        oldEntity.setLastUpdate(new Date());
        return FinanceInfoMapper.INSTANCE.toDto(finInfoRepository.save(oldEntity));
    }

    @Override
    @LogIt
    public List<FinanceInfoRes> list() {
        Collection<FinanceInfoEntity> optional = finInfoRepository.findAll();
        return optional.stream().collect(Collections.toCollection(ArrayList::new));
    }

    @Override
    @LogIt
    public FinanceInfoRes del(String uuid) {
        FinanceInfoEntity oldEntity = finInfoRepository.getLastFinInfoByUuid(uuid);
        finInfoRepository.delete(oldEntity);
        return FinanceInfoMapper.INSTANCE.toDto(oldEntity);
    }

}