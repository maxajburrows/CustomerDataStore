package com.CustomerDataStore.Services;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.CustomerDataStore.Entities.CustomerDataEntity;
import com.CustomerDataStore.Repositories.CustomerDataRepository;
import org.modelmapper.ModelMapper;

public class CustomerDataStoreServiceImpl implements CustomerDataStoreService {

    ModelMapper modelMapper = new ModelMapper();
    CustomerDataRepository customerDataRepository;
    public CustomerDataStoreServiceImpl(CustomerDataRepository customerDataRepository) {
        this.customerDataRepository = customerDataRepository;
    }
    @Override
    public CustomerResponseDto createCustomer(AddCustomerRequestDto newCustomer) {
        CustomerDataEntity newCustomerEntity = modelMapper.map(newCustomer, CustomerDataEntity.class);
        CustomerDataEntity storedCustomerEntity = customerDataRepository.save(newCustomerEntity);
        return modelMapper.map(storedCustomerEntity, CustomerResponseDto.class);
    }
}
