package com.smartosc.lending.lms.service.service;

import java.util.List;
import java.util.Optional;

import com.smartosc.lending.lms.service.entity.CrudEntity;
import com.smartosc.lending.lms.service.exception.NotFoundException;
import com.smartosc.lending.lms.service.dto.Crud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartosc.lending.lms.service.repository.IMongoCrudRepository;

@Service
public class MongoCrudService {

    @Autowired
    private IMongoCrudRepository repository;

    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<CrudEntity> list() {
        return (List<CrudEntity>) repository.findAll();
    }

    public CrudEntity get(String id) {
        Optional<CrudEntity> optionalCrud = repository.findById(id);
        return optionalCrud.orElseThrow(() -> new NotFoundException("Crud with id: " + id + " could not discovered"));
    }

    public CrudEntity upsert(Crud crudDto) {
        return repository.save(toEntity(crudDto));
    }

    public CrudEntity toEntity(Crud dto) {
        CrudEntity entity = new CrudEntity();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());

        return entity;
    }
}
