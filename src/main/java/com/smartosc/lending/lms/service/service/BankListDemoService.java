package com.smartosc.lending.lms.service.service;

import java.util.*;

import com.smartosc.lending.lms.service.dto.response.BankListDemoResponse;
import com.smartosc.lending.lms.service.entity.BankListDemoEntity;
import com.smartosc.lending.lms.service.exception.NotFoundException;
import com.smartosc.lending.lms.service.dto.request.BankListDemo;
import com.smartosc.lending.lms.service.repository.IBankListRepository;
import com.smartosc.lending.lms.service.util.IConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankListDemoService {

    @Autowired
    private IBankListRepository repository;

    public BankListDemoResponse insertOrUpdate(BankListDemo dto) {
        return toResponse(repository.save(toEntity(dto)));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<BankListDemoResponse> list() {
        List<BankListDemoResponse> result = new ArrayList<BankListDemoResponse>();
        List<BankListDemoEntity> bankListDemoEntityList = (List<BankListDemoEntity>) repository.findAll();
        bankListDemoEntityList.stream().forEach(entity -> {
            result.add(toResponse(entity));
        });
        return result;
    }

    public BankListDemoResponse getBankListDemoById(long id) {
        Optional<BankListDemoEntity> optionalBankListDemoEntity = repository.findById(id);
        return toResponse(optionalBankListDemoEntity.orElseThrow(() -> new NotFoundException(IConst.MSG_RESOURCE_NOTFOUND)));
    }

    private BankListDemoEntity toEntity(BankListDemo bankListDemoDto) {
        BankListDemoEntity entity = new BankListDemoEntity();
        entity.setBankName(bankListDemoDto.getBankName());
        entity.setType(bankListDemoDto.getType());
        entity.setCreated(new Date());

        return entity;
    }

    private BankListDemoResponse toResponse(BankListDemoEntity entity) {
        BankListDemoResponse resp = new BankListDemoResponse();
        resp.setNameOfbank(entity.getBankName());
        resp.setType(entity.getType());
        return resp;
    }

}
