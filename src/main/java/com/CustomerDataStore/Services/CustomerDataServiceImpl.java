package com.CustomerDataStore.Services;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.CustomerDataStore.Dtos.EditCustomerRequestDto;
import com.CustomerDataStore.Entities.CustomerDataEntity;
import com.CustomerDataStore.Exceptions.CustomerNotFoundException;
import com.CustomerDataStore.Exceptions.MissingRequestDetailsException;
import com.CustomerDataStore.Exceptions.NoCustomersException;
import com.CustomerDataStore.Repositories.CustomerDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerDataServiceImpl implements CustomerDataService {
    private static final String customerIdNotFoundMessage =
            "There is no customer with this customerId in the database!";
    private static final String emptyPatchRequestMessage =
            "You must provide either an address, an email address or both in the request body to update the user";
    private static final String emptySearchByNameRequestMessage =
            "You must provide either a firstname, lastname or both in the URI to search for users by name";
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

    /* Could use parallel stream for large datasets (get customers and searchByName methods).
    Not worth it for small datasets due to additional overhead and thread safety concerns. */
    @Override
    public List<CustomerResponseDto> getCustomers() {
        List<CustomerDataEntity> customers = customerDataRepo.findAll();
        checkIfAnyCustomersFound(customers);
        return customers.stream()
                .map(CustomerResponseDto::new)
                .toList();
    }


    @Override
    public List<CustomerResponseDto> searchByName(String firstName, String lastName) {
        if (firstName == null && lastName == null) {
            throw new MissingRequestDetailsException(emptySearchByNameRequestMessage);
        }

        List<CustomerDataEntity> customers =
                (firstName == null) ? customerDataRepo.findByLastNameIgnoreCase(lastName.trim()) :
                        (lastName == null) ? customerDataRepo.findByFirstNameIgnoreCase(firstName.trim()) :
                                customerDataRepo.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(
                                        firstName.trim(), lastName.trim());
        checkIfAnyCustomersFound(customers);

        return customers.stream()
                .map(CustomerResponseDto::new)
                .toList();
    }

    @Override
    public CustomerResponseDto updateCustomer(long customerId, EditCustomerRequestDto newInformation) {
        if ((newInformation.address() == null && newInformation.emailAddress() == null)) {
            throw new MissingRequestDetailsException(emptyPatchRequestMessage);
        }

        CustomerDataEntity customer = fetchCustomerFromDB(customerId);
        if (newInformation.emailAddress() != null) {
            customer.setEmailAddress(newInformation.emailAddress());
        }
        if (newInformation.address() != null) {
            addNewAddress(customer, newInformation.address());
        }
        return new CustomerResponseDto(customerDataRepo.save(customer));
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

    private static void checkIfAnyCustomersFound(List<CustomerDataEntity> customers) {
        if (customers.isEmpty()) {
            throw new NoCustomersException();
        }
    }
}