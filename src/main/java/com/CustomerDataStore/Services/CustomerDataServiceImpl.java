package com.CustomerDataStore.Services;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.CustomerDataStore.Entities.CustomerDataEntity;
import com.CustomerDataStore.Repositories.CustomerDataRepository;
import org.modelmapper.ModelMapper;

public class CustomerDataServiceImpl implements CustomerDataService {
    ModelMapper modelMapper = new ModelMapper();
    CustomerDataRepository customerDataRepo;
    public CustomerDataServiceImpl(CustomerDataRepository customerDataRepo) {
        this.customerDataRepo = customerDataRepo;
    }
    @Override
    public CustomerResponseDto createCustomer(AddCustomerRequestDto newCustomer) {
        CustomerDataEntity newCustomerEntity = modelMapper.map(newCustomer, CustomerDataEntity.class);
        CustomerDataEntity storedCustomerEntity = customerDataRepo.save(newCustomerEntity);
        return modelMapper.map(storedCustomerEntity, CustomerResponseDto.class);
    }
}
