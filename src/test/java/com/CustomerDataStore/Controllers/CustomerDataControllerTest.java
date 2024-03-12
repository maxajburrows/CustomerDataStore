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

    private static final String customerIdNotFoundMessage =
            "There is no customer with this customerId in the database!";
    private static final String baseURI = "/customers";
    private long customerId1;
    AddCustomerRequestDto customer1;
    AddCustomerRequestDto customer2;
    HttpHeaders headers;
    @BeforeAll
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(1)
    @DisplayName("Get all customers - DB empty")
    void testGetAllCustomers_whenNoCustomersExist_204Returned() {
        ResponseEntity response = testRestTemplate.getForEntity(baseURI, null);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("Add customer - valid details")
    void testCreateCustomer_whenValidCustomerDetailsProvided_ReturnsCustomerDetails() throws JsonProcessingException {
        customer1 = new AddCustomerRequestDto(
                "Max",
                "Burrows",
                26,
                List.of("A street somewhere"),
                "max@notMyRealEmail.com"
        );
        HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(customer1), headers);

        ResponseEntity<CustomerResponseDto> response = testRestTemplate.postForEntity(baseURI,
                request,
                CustomerResponseDto.class);
        CustomerResponseDto createdCustomer = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        customerId1 = createdCustomer.customerId();
        assertNotNull(createdCustomer.customerId(),
                "Created customer should have a customerId");
        assertEquals(customer1.firstName(), createdCustomer.firstName(),
                "Created customer first name did not match request");
        assertEquals(customer1.lastName(), createdCustomer.lastName(),
                "Created customer last name did not match request");
        assertEquals(customer1.age(), createdCustomer.age(),
                "Created customer age did not match request");
        assertEquals(customer1.address().get(0), createdCustomer.address().get(0),
                "Created customer address did not match request");
        assertEquals(customer1.emailAddress(), createdCustomer.emailAddress(),
                "Created customer email address did not match request");
    }

    @Test
    @Order(3)
    @DisplayName("Get customer by Id - valid Id")
    void testGetCustomerById_whenValidIdProvided_correctCustomerIsReturned() {
        ResponseEntity<CustomerResponseDto> response = testRestTemplate.getForEntity(baseURI+"/"+customerId1,
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
        assertEquals(customer1.address().get(0), retrievedCustomer.address().get(0),
                "Retrieved customer address did not match posted address");
        assertEquals(customer1.emailAddress(), retrievedCustomer.emailAddress(),
                "Retrieved customer email address did not match posted email address");
    }

    @Test
    @Order(4)
    @DisplayName("Get customer - invalid id throws exception")
    void testGetCustomerById_whenInvalidIdProvided_404ReturnedWithMessage() {
        // TODO: May be better to do this test when the database isn't empty (maybe as well).
        ResponseEntity<String> response = testRestTemplate.getForEntity(baseURI+"/1"+customerId1,
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(customerIdNotFoundMessage, response.getBody());
    }


    @Test
    @Order(5)
    @DisplayName("Get all customers - DB populated")
    void testGetAllCustomers_ifCustomersInDB_returnsAllCustomers() throws JsonProcessingException {
        customer2 = new AddCustomerRequestDto(
                "Amelia",
                "Burrows",
                24,
                List.of("Not with me anymore"),
                "amelia@notHerRealEmailEither.com"
        );
        HttpEntity<String> postRequest = new HttpEntity<>(new ObjectMapper().writeValueAsString(customer2), headers);
        long customerId2 = testRestTemplate.postForEntity(baseURI,
                        postRequest,
                        CustomerResponseDto.class)
                .getBody()
                .customerId();

        HttpEntity getRequest = new HttpEntity(null, headers);
        ResponseEntity<List<CustomerResponseDto>> response = testRestTemplate.exchange(baseURI,
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
        assertEquals(customer2.address().get(0), customers.get(1).address().get(0),
                "Retrieved customer address did not match posted address");
        assertEquals(customer2.emailAddress(), customers.get(1).emailAddress(),
                "Retrieved customer email address did not match posted email address");
    }

    @Test
    @Order(6)
    @DisplayName("Search by name - valid first and last name")
    void testSearchByName_whenFirstAndLastNameProvided_correctCustomersAreReturned() {
        String firstName = customer1.firstName();
        String lastName = customer1.lastName();
        String requestURI = baseURI+"/search-by-name"+"?first-name="+firstName+"&last-name="+lastName;
        HttpEntity getRequestEntity = new HttpEntity(null, headers);
        ResponseEntity<List<CustomerResponseDto>> response = testRestTemplate.exchange(requestURI,
                HttpMethod.GET,
                getRequestEntity,
                new ParameterizedTypeReference<List<CustomerResponseDto>>() {
                });
        List<CustomerResponseDto> retrievedCustomer = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(retrievedCustomer.isEmpty());
        assertEquals(customerId1, retrievedCustomer.get(0).customerId(),
                "Retrieved customerId did not match posted customerId");
        assertEquals(customer1.firstName(), retrievedCustomer.get(0).firstName(),
                "Retrieved customer first name did not match posted first name");
        assertEquals(customer1.lastName(), retrievedCustomer.get(0).lastName(),
                "Retrieved customer last name did not match posted last name");
        assertEquals(customer1.age(), retrievedCustomer.get(0).age(),
                "Retrieved customer age did not match posted age");
        assertEquals(customer1.address().get(0), retrievedCustomer.get(0).address().get(0),
                "Retrieved customer address did not match posted address");
        assertEquals(customer1.emailAddress(), retrievedCustomer.get(0).emailAddress(),
                "Retrieved customer email address did not match posted email address");
    }

    @Test
    @Order(7)
    @DisplayName("Search by name - last name only")
    void testSearchByName_whenOnlyLastNameProvided_correctCustomersAreReturned() {
        String lastName = customer1.lastName();
        String requestURI = baseURI+"/search-by-name"+"?last-name="+lastName;
        HttpEntity getRequestEntity = new HttpEntity(null, headers);
        ResponseEntity<List<CustomerResponseDto>> response = testRestTemplate.exchange(requestURI,
                HttpMethod.GET,
                getRequestEntity,
                new ParameterizedTypeReference<List<CustomerResponseDto>>() {
                });
        List<CustomerResponseDto> retrievedCustomer = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, retrievedCustomer.size());
        assertEquals(customerId1, retrievedCustomer.get(0).customerId(),
                "Retrieved customerId did not match posted customerId");
        assertEquals(customer2.firstName(), retrievedCustomer.get(1).firstName(),
                "Retrieved customer first name did not match posted first name");
        assertEquals(customer2.lastName(), retrievedCustomer.get(1).lastName(),
                "Retrieved customer last name did not match posted last name");
        assertEquals(customer2.age(), retrievedCustomer.get(1).age(),
                "Retrieved customer age did not match posted age");
        assertEquals(customer2.address().get(0), retrievedCustomer.get(1).address().get(0),
                "Retrieved customer address did not match posted address");
        assertEquals(customer1.emailAddress(), retrievedCustomer.get(0).emailAddress(),
                "Retrieved customer email address did not match posted email address");
    }

    @Test
    @Order(8)
    @DisplayName("Search by name - first name only")
    void testSearchByName_whenOnlyFirstNameProvided_correctCustomersAreReturned() {
        String firstName = customer2.firstName();
        String requestURI = baseURI+"/search-by-name"+"?first-name="+firstName;
        HttpEntity getRequestEntity = new HttpEntity(null, headers);
        ResponseEntity<List<CustomerResponseDto>> response = testRestTemplate.exchange(requestURI,
                HttpMethod.GET,
                getRequestEntity,
                new ParameterizedTypeReference<List<CustomerResponseDto>>() {
                });
        List<CustomerResponseDto> retrievedCustomer = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, retrievedCustomer.size());
        assertEquals(customer2.firstName(), retrievedCustomer.get(0).firstName(),
                "Retrieved customer first name did not match posted first name");
        assertEquals(customer2.lastName(), retrievedCustomer.get(0).lastName(),
                "Retrieved customer last name did not match posted last name");
        assertEquals(customer2.age(), retrievedCustomer.get(0).age(),
                "Retrieved customer age did not match posted age");
        assertEquals(customer2.address().get(0), retrievedCustomer.get(0).address().get(0),
                "Retrieved customer address did not match posted address");
        assertEquals(customer2.emailAddress(), retrievedCustomer.get(0).emailAddress(),
                "Retrieved customer email address did not match posted email address");
    }
}