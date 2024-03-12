package com.CustomerDataStore.Services;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.CustomerDataStore.Dtos.EditCustomerRequestDto;
import com.CustomerDataStore.Entities.CustomerDataEntity;
import com.CustomerDataStore.Exceptions.CustomerNotFoundException;
import com.CustomerDataStore.Exceptions.NoCustomersException;
import com.CustomerDataStore.Repositories.CustomerDataRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerDataServiceImpl implements CustomerDataService {
    String customerIdNotFoundMessage = "There is no customer with this customerId in the database!";
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
        return new CustomerResponseDto(fetchCustomerFromDB(customerId));
    }


    /* Could use parallel stream for large datasets.
    Not worth it for small datasets due to additional overhead and thread safety concerns. */
    @Override
    public List<CustomerResponseDto> getCustomers() {
        List<CustomerDataEntity> customers = customerDataRepo.findAll();
        if (customers.isEmpty()) {
            throw new NoCustomersException();
        }
        return customers.stream()
                .map(CustomerResponseDto::new)
                .toList();
    }

    @Override
    public CustomerResponseDto updateCustomer(long customerId, EditCustomerRequestDto newInformation) {
        CustomerDataEntity customer = fetchCustomerFromDB(customerId);
        if (newInformation.emailAddress() != null) {
            customer.setEmailAddress(newInformation.emailAddress());
        }
        if (newInformation.address() != null) {
            addNewAddress(customer, newInformation.address());
        }
        return new CustomerResponseDto(customerDataRepo.save(customer));
    }

    @Override
    public List<CustomerResponseDto> searchByName(String firstName, String lastName) {
        return null;
    }

    public void addNewAddress(CustomerDataEntity customerToUpdate, String newAddress) {
        List<String> addressList = customerToUpdate.getAddress();
        for (String address : addressList) {
            if (address.trim().compareToIgnoreCase(newAddress.trim()) == 0) return;
        }
        addressList.add(newAddress);
        customerToUpdate.setAddress(addressList);
    }

    private CustomerDataEntity fetchCustomerFromDB(long customerId) {
        Optional<CustomerDataEntity> customerDataOptional = customerDataRepo.findById(customerId);
        if (customerDataOptional.isEmpty()) {
            throw new CustomerNotFoundException(customerIdNotFoundMessage);
        }
        return customerDataOptional.get();
    }
}
