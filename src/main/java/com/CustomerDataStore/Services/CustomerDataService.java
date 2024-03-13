package com.CustomerDataStore.Services;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.CustomerDataStore.Dtos.EditCustomerRequestDto;

import java.util.List;

public interface CustomerDataService {
    CustomerResponseDto createCustomer(AddCustomerRequestDto newCustomer);
    CustomerResponseDto getCustomerById(long customerId);
    List<CustomerResponseDto> getCustomers();
    CustomerResponseDto updateCustomer(long customerId, EditCustomerRequestDto newInformation);
    List<CustomerResponseDto> searchByName(String firstName, String lastName);
}
