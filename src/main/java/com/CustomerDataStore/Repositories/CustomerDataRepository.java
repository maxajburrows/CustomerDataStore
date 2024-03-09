package com.CustomerDataStore.Repositories;

import com.CustomerDataStore.Entities.CustomerDataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDataRepository extends CrudRepository<CustomerDataEntity, Long> {
}
