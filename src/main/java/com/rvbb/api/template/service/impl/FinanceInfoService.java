package com.rvbb.api.template.service.impl;

import com.rvbb.api.template.common.constant.FinanceInfoStatus;
import com.rvbb.api.template.common.util.LogIt;
import com.rvbb.api.template.dto.FinanceInfoInput;
import com.rvbb.api.template.dto.FinanceInfoRes;
import com.rvbb.api.template.entity.FinanceInfoEntity;
import com.rvbb.api.template.repository.FinanceInfoRepository;
import com.rvbb.api.template.repository.FinanceInfoXpanRepository;
import com.rvbb.api.template.service.IFinanceInfoService;
import com.rvbb.api.template.service.mapper.FinanceInfoMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.toCollection;
import static java.util.UUID.randomUUID;

@Service
@AllArgsConstructor
public class FinanceInfoService implements IFinanceInfoService {

    private final FinanceInfoRepository finInfoRepository;
    private final FinanceInfoXpanRepository financeInfoXpanseRepository;

    @Override
    @LogIt
    public FinanceInfoRes getLast() {
        FinanceInfoEntity entity = finInfoRepository.getLast();
        if (ObjectUtils.isEmpty(entity)) {
            throw new EntityNotFoundException("Not found last financial information");
        }
        return FinanceInfoMapper.INSTANCE.toDto(entity);
    }

    @Override
    @LogIt
    public FinanceInfoRes get(String uuid) {
        FinanceInfoEntity financeInfo = getByUuid(uuid);
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
                .status(FinanceInfoStatus.XX.getValue())
                .uuid(randomUUID().toString())
                .build();
        finInfoRepository.save(newEntity);
        return FinanceInfoMapper.INSTANCE.toDto(newEntity);
    }

    @Override
    @LogIt
    public FinanceInfoRes update(String uuid, FinanceInfoInput request) {
        double expense = Math.floor(Double.valueOf(request.getExpense()) * 100) / 100;
        double preTaxIncome = Math.floor(Double.valueOf(request.getPreTaxIncome()) * 100) / 100;
        FinanceInfoEntity financeInfo = getByUuid(uuid);
        financeInfo.setPreTaxIncome(BigDecimal.valueOf(preTaxIncome));
        financeInfo.setCompanyAddress(request.getCompanyAddress());
        financeInfo.setCompanyName(request.getCompanyName());
        financeInfo.setExpense(BigDecimal.valueOf(expense));
        financeInfo.setLastUpdate(new Date());
        financeInfo.setStatus(request.getStatus());
        return FinanceInfoMapper.INSTANCE.toDto(finInfoRepository.save(financeInfo));
    }

    @Override
    @LogIt
    public List<FinanceInfoRes> list() {
        Collection<FinanceInfoEntity> collection = finInfoRepository.findAll();
        Collection<FinanceInfoRes> response = FinanceInfoMapper.INSTANCE.mapToListJobInformationDto(collection);
        return response.stream().collect(toCollection(ArrayList::new));
    }

    @Override
    @LogIt
    public FinanceInfoRes del(String uuid) {
        FinanceInfoEntity oldEntity = getByUuid(uuid);
        finInfoRepository.delete(oldEntity);
        return FinanceInfoMapper.INSTANCE.toDto(oldEntity);
    }

    private FinanceInfoEntity getByUuid(String uuid){
        Optional<FinanceInfoEntity> optional = finInfoRepository.findByUuid(uuid);
        return optional.orElseThrow(
                () -> new EntityNotFoundException("Not found finance information with uuid : " + uuid));
    }

}