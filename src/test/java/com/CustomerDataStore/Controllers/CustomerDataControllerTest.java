package com.CustomerDataStore.Controllers;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerDataControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private long customerId1;
    AddCustomerRequestDto customer1;
    String customerIdNotFoundMessage = "There is not customer with this customerId in the database!";
    HttpHeaders headers;
    @BeforeAll
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    }
    @Test
    @Order(3)
    void testCreateCustomer_whenValidCustomerDetailsProvided_ReturnsCustomerDetails() throws JsonProcessingException {
        customer1 = new AddCustomerRequestDto(
                "Max",
                "Burrows",
                26,
                "A street somewhere",
                "max@notMyRealEmail.com"
        );
        HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(customer1), headers);

        ResponseEntity<CustomerResponseDto> response = testRestTemplate.postForEntity("/customers",
                request,
                CustomerResponseDto.class);
        CustomerResponseDto createdCustomer = response.getBody();
        customerId1 = createdCustomer.customerId();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(createdCustomer.customerId(),
                "Created customer should have a customerId");
        assertEquals(customer1.firstName(), createdCustomer.firstName(),
                "Created customer first name did not match request");
        assertEquals(customer1.lastName(), createdCustomer.lastName(),
                "Created customer last name did not match request");
        assertEquals(customer1.age(), createdCustomer.age(),
                "Created customer age did not match request");
        assertEquals(customer1.address(), createdCustomer.address(),
                "Created customer address did not match request");
        assertEquals(customer1.emailAddress(), createdCustomer.emailAddress(),
                "Created customer email address did not match request");
    }
    // TODO: Add display names to tests.
    @Test
    @Order(4)
    void testGetCustomerById_whenValidIdProvided_CorrectCustomerIsReturned() {
        ResponseEntity<CustomerResponseDto> response = testRestTemplate.getForEntity("/customers/"+customerId1,
                CustomerResponseDto.class);
        CustomerResponseDto retrievedCustomer = response.getBody();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerId1, retrievedCustomer.customerId(),
                "Retrieved customerId did not match posted customerId");
        assertEquals(customer1.firstName(), retrievedCustomer.firstName(),
                "Retrieved customer first name did not match posted first name");
        assertEquals(customer1.lastName(), retrievedCustomer.lastName(),
                "Retrieved customer last name did not match posted last name");
        assertEquals(customer1.age(), retrievedCustomer.age(),
                "Retrieved customer age did not match posted age");
        assertEquals(customer1.address(), retrievedCustomer.address(),
                "Retrieved customer address did not match posted address");
        assertEquals(customer1.emailAddress(), retrievedCustomer.emailAddress(),
                "Retrieved customer email address did not match posted email address");
    }

    @Test
    @Order(1)
    void testGetCustomerById_whenInvalidIdProvided_404ReturnedWithMessage() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/customers/1",
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(customerIdNotFoundMessage, response.getBody());
    }

    @Test
    @Order(5)
    void testGetAllCustomers_ifCustomersInDB_returnsAllCustomers() throws JsonProcessingException {
        AddCustomerRequestDto customer2 = new AddCustomerRequestDto(
                "Amelia",
                "Burrows",
                24,
                "Not with me anymore",
                "amelia@notHerRealEmailEither.com"
        );
        HttpEntity<String> postRequest = new HttpEntity<>(new ObjectMapper().writeValueAsString(customer2), headers);
        long customerId2 = testRestTemplate.postForEntity("/customers",
                        postRequest,
                        CustomerResponseDto.class)
                .getBody()
                .customerId();

        HttpEntity getRequest = new HttpEntity(null, headers);
        ResponseEntity<List<CustomerResponseDto>> response = testRestTemplate.exchange("/customers",
                HttpMethod.GET,
                getRequest,
                new ParameterizedTypeReference<List<CustomerResponseDto>>() {
                });
        List<CustomerResponseDto> customers = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, customers.size(),
                "The incorrect number of customers is being returned");
        assertEquals(customerId1, customers.get(0).customerId(),
                "Retrieved customerId did not match posted customerId");
        assertEquals(customer1.firstName(), customers.get(0).firstName(),
                "Retrieved customer first name did not match posted first name");
        assertEquals(customer2.lastName(), customers.get(1).lastName(),
                "Retrieved customer last name did not match posted last name");
        assertEquals(customer2.age(), customers.get(1).age(),
                "Retrieved customer age did not match posted age");
        assertEquals(customer2.address(), customers.get(1).address(),
                "Retrieved customer address did not match posted address");
        assertEquals(customer2.emailAddress(), customers.get(1).emailAddress(),
                "Retrieved customer email address did not match posted email address");
    }

    @Test
    @Order(2)
    void testGetAllCustomers_whenNoCustomersExist_204Returned() {
        ResponseEntity response = testRestTemplate.getForEntity("/customers", null);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}