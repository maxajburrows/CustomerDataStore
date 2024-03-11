package com.CustomerDataStore.Services;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.CustomerDataStore.Entities.CustomerDataEntity;
import com.CustomerDataStore.Exceptions.CustomerNotFoundException;
import com.CustomerDataStore.Repositories.CustomerDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerDataServiceImpl implements CustomerDataService {
    String customerIdNotFoundMessage = "There is not customer with this customerId in the database!";
    CustomerDataRepository customerDataRepo;
    public CustomerDataServiceImpl(CustomerDataRepository customerDataRepo) {
        this.customerDataRepo = customerDataRepo;
    }
    @Override
    public CustomerResponseDto createCustomer(AddCustomerRequestDto newCustomer) {
        CustomerDataEntity storedCustomerEntity = customerDataRepo.save(new CustomerDataEntity(newCustomer));
        return new CustomerResponseDto(storedCustomerEntity);
    }

    @Override
    public CustomerResponseDto getCustomerById(long customerId) {
        Optional<CustomerDataEntity> customerData = customerDataRepo.findById(customerId);
        if (customerData.isEmpty()) {
            throw new CustomerNotFoundException(customerIdNotFoundMessage);
        }
        return new CustomerResponseDto(customerData.get());
    }

    @Override
    public List<CustomerResponseDto> getCustomers() {
        Iterable<CustomerDataEntity> customers = customerDataRepo.findAll();
        return StreamSupport.stream(customers.spliterator(), false)
                .map(CustomerResponseDto::new)
                .toList();
    }
}
