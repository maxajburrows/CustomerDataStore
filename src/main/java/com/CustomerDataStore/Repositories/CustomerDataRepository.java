package com.CustomerDataStore.Repositories;

import com.CustomerDataStore.Entities.CustomerDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDataRepository extends JpaRepository<CustomerDataEntity, Long> {
    List<CustomerDataEntity> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
    List<CustomerDataEntity> findByFirstNameIgnoreCase(String firstName);
    List<CustomerDataEntity> findByLastNameIgnoreCase(String lastName);
}
