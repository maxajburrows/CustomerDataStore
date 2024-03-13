package com.CustomerDataStore.Controllers;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Services.CustomerDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = CustomerDataController.class)
public class CustomerDataControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    CustomerDataService customerDataService;
    private AddCustomerRequestDto customer1;
    private static final String nullEmailErrorMessage =
            "Customer must have an email address!";
    private static final String noAddressErrorMessage =
            "Customer must have at least one address!";
    private static final String firstNameTooLongErrorMessage =
            "First name must be between 2 and 30 characters";
    private static final String ageTooLowErrorMessage =
            "Customer must be at least 10 years old!";

    @BeforeEach
    void setUp() {
        customer1 = new AddCustomerRequestDto(
                "Ryan",
                "Gosling",
                38,
                new ArrayList<>(Arrays.asList("Hollywood")),
                null
        );
    }

    @Test
    @DisplayName("Post request - email null")
    void testCreateCustomer_whenNullEmailAddressProvided_400AndErrorMessageReturned() throws Exception {
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customer1));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse mockResponse = mvcResult.getResponse();
        List<String> errorMessages = new ObjectMapper()
                .readValue(mockResponse.getContentAsString(), new TypeReference<List<String>>(){});

        assertEquals(HttpStatus.BAD_REQUEST.value(), mockResponse.getStatus());
        assertEquals(nullEmailErrorMessage, errorMessages.get(0));
    }

    @Test
    @DisplayName("Post request - empty address field")
    void testCreateCustomer_whenNoAddressProvided_400AndErrorMessageReturned() throws Exception {
        AddCustomerRequestDto customer2 = new AddCustomerRequestDto(
                "Ryan",
                "Gosling",
                38,
                new ArrayList<>(),
                "email@email.com"
        );
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customer2));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse mockResponse = mvcResult.getResponse();
        List<String> errorMessages = new ObjectMapper()
                .readValue(mockResponse.getContentAsString(), new TypeReference<List<String>>(){});

        assertEquals(HttpStatus.BAD_REQUEST.value(), mockResponse.getStatus());
        assertEquals(noAddressErrorMessage, errorMessages.get(0));
    }

    @Test
    @DisplayName("Post request - multiple invalid fields")
    void testCreateCustomer_whenMultipleInvalidFieldsProvided_400AndAllErrorMessageReturned() throws Exception {
        AddCustomerRequestDto customer3 = new AddCustomerRequestDto(
                "ReallyReallyLongNameReallyReallyLongNameReallyReallyLongNameReallyReallyLongName",
                "Gosling",
                5,
                null,
                "email@email.com"
        );
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customer3));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse mockResponse = mvcResult.getResponse();
        List<String> errorMessages = new ObjectMapper()
                .readValue(mockResponse.getContentAsString(), new TypeReference<List<String>>(){});

        assertEquals(HttpStatus.BAD_REQUEST.value(), mockResponse.getStatus());
        assertEquals(noAddressErrorMessage, errorMessages.get(0));
        assertEquals(firstNameTooLongErrorMessage, errorMessages.get(1));
        assertEquals(ageTooLowErrorMessage, errorMessages.get(2));
    }
}
