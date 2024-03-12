package com.CustomerDataStore.Services;

import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.CustomerDataStore.Dtos.EditCustomerRequestDto;
import com.CustomerDataStore.Entities.CustomerDataEntity;
import com.CustomerDataStore.Repositories.CustomerDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerDataServiceImplTest {

    @Mock
    CustomerDataRepository customerDataRepository;
    @InjectMocks
    CustomerDataServiceImpl customerDataService;

    CustomerDataEntity customer;
    String address1;
    String address2;

    @BeforeEach
    void setUp() {
        customer = new CustomerDataEntity();
        customer.setCustomerId(1);
        customer.setFirstName("Dan");
        customer.setLastName("Brown");
        customer.setAge(55);
        address1 = " Address 1";
        address2 = "Address 2";
        customer.setAddress(new ArrayList<>(Arrays.asList(address1, address2)));
        customer.setEmailAddress("test@test.com");
    }
    @Test
    @DisplayName("Add new address - unique address")
    void testAddNewAddress_whenUniqueNewAddressProvided_newAddressAddedToCustomerEntity() {
        String newAddress = "Address 3";

        customerDataService.addNewAddress(customer, newAddress);
        List<String> customerAddresses = customer.getAddress();

        assertEquals(3, customerAddresses.size());
        assertEquals(address1, customerAddresses.get(0));
        assertEquals(address2, customerAddresses.get(1));
        assertEquals(newAddress, customerAddresses.get(2));
    }

    @Test
    @DisplayName("Duplicated new address - different case")
    void testAddNewAddress_whenDuplicateAddressWithDifferentCaseProvided_customerUnchanged() {
        String newAddress = "adDress 2";

        customerDataService.addNewAddress(customer, newAddress);
        List<String> customerAddresses = customer.getAddress();

        assertEquals(2, customerAddresses.size());
        assertEquals(address1, customerAddresses.get(0));
        assertEquals(address2, customerAddresses.get(1));
    }

    @Test
    @DisplayName("Duplicated new address - additional whitespace")
    void testAddNewAddress_whenDuplicateAddressWithWhiteSpaceProvided_customerUnchanged() {
        String newAddress = " Address 1    ";

        customerDataService.addNewAddress(customer, newAddress);
        List<String> customerAddresses = customer.getAddress();

        assertEquals(2, customerAddresses.size());
        assertEquals(address1, customerAddresses.get(0));
        assertEquals(address2, customerAddresses.get(1));
    }

    @Test
    @DisplayName("Update address and email address")
    void testUpdateCustomer_whenValidAddressAndEmailAddressProvided_bothUpdated() {
        when(customerDataRepository.findById(any(long.class)))
                .thenReturn(Optional.ofNullable(customer));
        when(customerDataRepository.save(any(CustomerDataEntity.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        String newAddress = "Number 5, Street 2, Amsterdam";
        String newEmailAddress = "anotherTest@nextTest.com";
        EditCustomerRequestDto newInformation = new EditCustomerRequestDto(newAddress, newEmailAddress);

        CustomerResponseDto updatedCustomer = customerDataService.updateCustomer(1, newInformation);

        assertEquals(customer.getCustomerId(), updatedCustomer.customerId());
        assertEquals(customer.getFirstName(), updatedCustomer.firstName());
        assertEquals(customer.getLastName(), updatedCustomer.lastName());
        assertEquals(customer.getAge(), updatedCustomer.age());
        assertEquals(address1, updatedCustomer.address().get(0));
        assertEquals(address2, updatedCustomer.address().get(1));
        assertEquals(newAddress, updatedCustomer.address().get(2));
        assertEquals(newEmailAddress, updatedCustomer.emailAddress());
    }

    @Test
    @DisplayName("Update address only")
    void testUpdateCustomer_whenValidAddressOnlyProvided_addressOnlyUpdated() {
        when(customerDataRepository.findById(any(long.class)))
                .thenReturn(Optional.ofNullable(customer));
        when(customerDataRepository.save(any(CustomerDataEntity.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        String newAddress = "3, GreatStraat, Weesp";
        EditCustomerRequestDto newInformation = new EditCustomerRequestDto(newAddress, null);

        CustomerResponseDto updatedCustomer = customerDataService.updateCustomer(1, newInformation);

        assertEquals(customer.getCustomerId(), updatedCustomer.customerId());
        assertEquals(customer.getFirstName(), updatedCustomer.firstName());
        assertEquals(customer.getLastName(), updatedCustomer.lastName());
        assertEquals(customer.getAge(), updatedCustomer.age());
        assertEquals(address1, updatedCustomer.address().get(0));
        assertEquals(address2, updatedCustomer.address().get(1));
        assertEquals(newAddress, updatedCustomer.address().get(2));
        assertEquals(customer.getEmailAddress(), updatedCustomer.emailAddress());
    }

    @Test
    @DisplayName("Update email address only")
    void testUpdateCustomer_whenValidEmailAddressOnlyProvided_emailAddressOnlyUpdated() {
        when(customerDataRepository.findById(any(long.class)))
                .thenReturn(Optional.ofNullable(customer));
        when(customerDataRepository.save(any(CustomerDataEntity.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        String newEmailAddress = "google@gmail.com";
        EditCustomerRequestDto newInformation = new EditCustomerRequestDto(null, newEmailAddress);

        CustomerResponseDto updatedCustomer = customerDataService.updateCustomer(1, newInformation);

        assertEquals(customer.getCustomerId(), updatedCustomer.customerId());
        assertEquals(customer.getFirstName(), updatedCustomer.firstName());
        assertEquals(customer.getLastName(), updatedCustomer.lastName());
        assertEquals(customer.getAge(), updatedCustomer.age());
        assertEquals(address1, updatedCustomer.address().get(0));
        assertEquals(address2, updatedCustomer.address().get(1));
        assertEquals(newEmailAddress, updatedCustomer.emailAddress());
    }
}