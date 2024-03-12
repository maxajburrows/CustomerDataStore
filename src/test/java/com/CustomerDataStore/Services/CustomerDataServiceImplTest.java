package com.CustomerDataStore.Services;

import com.CustomerDataStore.Entities.CustomerDataEntity;
import com.CustomerDataStore.Repositories.CustomerDataRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDataServiceImplTest {

    @Mock
    CustomerDataRepository customerDataRepository;
    //@InjectMocks
    CustomerDataServiceImpl customerDataService = new CustomerDataServiceImpl(customerDataRepository);

    CustomerDataEntity customer;
    String address1;
    String address2;

    @BeforeEach
    void setUp() {
        customer = new CustomerDataEntity();
        customer.setCustomerId(1);
        customer.setFirstName("Dan");
        customer.setLastName("Brown");
        address1 = "Address 1";
        address2 = "Address 2";
        customer.setAddress(List.of(address1, address2));
        customer.setEmailAddress("test@test.com");
    }
    @Test
    void testAddNewAddress_whenUniqueNewAddressProvided_newAddressAddedToCustomerEntity() {
        String newAddress = "Address 3";

        customerDataService.addNewAddress(customer, newAddress);
        List<String> customerAddresses = customer.getAddress();

        assertEquals(3, customerAddresses.size());
        assertEquals(address1, customerAddresses.get(0));
        assertEquals(address2, customerAddresses.get(1));
        assertEquals(newAddress, customerAddresses.get(2));
    }

}