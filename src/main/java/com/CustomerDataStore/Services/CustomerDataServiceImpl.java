package com.CustomerDataStore.Services;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.CustomerDataStore.Entities.CustomerDataEntity;
import com.CustomerDataStore.Repositories.CustomerDataRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerDataServiceImpl implements CustomerDataService {
    CustomerDataRepository customerDataRepo;
    public CustomerDataServiceImpl(CustomerDataRepository customerDataRepo) {
        this.customerDataRepo = customerDataRepo;
    }
    @Override
    public CustomerResponseDto createCustomer(AddCustomerRequestDto newCustomer) {
        CustomerDataEntity storedCustomerEntity = customerDataRepo.save(new CustomerDataEntity(newCustomer));
        return new CustomerResponseDto(storedCustomerEntity);
    }
}
