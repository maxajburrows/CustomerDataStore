package com.CustomerDataStore.Repositories;

import com.CustomerDataStore.Entities.CustomerDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDataRepository extends JpaRepository<CustomerDataEntity, Long> {
    List<CustomerDataEntity> findByFirstNameAndLastName(String firstName, String lastName);
    List<CustomerDataEntity> findByFirstName(String firstName);
    List<CustomerDataEntity> findByLastName(String lastName);
}
