package com.CustomerDataStore.Services;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface CustomerDataService {
    CustomerResponseDto createCustomer(AddCustomerRequestDto newCustomer);
}
