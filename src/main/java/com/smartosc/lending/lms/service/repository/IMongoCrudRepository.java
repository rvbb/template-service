package com.smartosc.lending.lms.service.repository;

import com.smartosc.lending.lms.service.entity.CrudEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMongoCrudRepository extends MongoRepository<CrudEntity, String> {

}
