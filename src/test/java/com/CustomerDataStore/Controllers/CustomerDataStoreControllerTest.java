package com.CustomerDataStore.Controllers;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerDataControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testCreateCustomer_whenValidCustomerDetailsProvided_ReturnsCustomerDetails() throws JsonProcessingException {
        AddCustomerRequestDto newCustomer = new AddCustomerRequestDto(
                "Max",
                "Burrows",
                26,
                "A street somewhere",
                "max@notMyRealEmail.com"
        );
        HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(newCustomer));

        ResponseEntity<CustomerResponseDto> response = testRestTemplate.postForEntity("/customers",
                request,
                CustomerResponseDto.class);
        CustomerResponseDto createdCustomer = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(createdCustomer.customerId(),
                "Created customer should have a customerId");
        assertEquals(newCustomer.firstName(), createdCustomer.firstName(),
                "Created customer first name did not match request");
        assertEquals(newCustomer.lastName(), createdCustomer.lastName(),
                "Created customer last name did not match request");
        assertEquals(newCustomer.age(), createdCustomer.age(),
                "Created customer age did not match request");
        assertEquals(newCustomer.Address(), createdCustomer.Address(),
                "Created customer address did not match request");
        assertEquals(newCustomer.emailAddress(), createdCustomer.emailAddress(),
                "Created customer email address did not match request");
    }

}