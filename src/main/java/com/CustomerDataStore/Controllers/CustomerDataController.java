package com.CustomerDataStore.Controllers;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.CustomerDataStore.Dtos.EditCustomerRequestDto;
import com.CustomerDataStore.Services.CustomerDataService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerDataController {

    CustomerDataService customerDataService;
    public CustomerDataController(CustomerDataService customerDataService) {
        this.customerDataService = customerDataService;
    }
    // Get all customers
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        // Implementation required
        return null;
    }

    // Get customer by id
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable long id) {
        return ResponseEntity.ok(customerDataService.getCustomerById(id));
    }

    // Get by last name
    @GetMapping("/{lastName}")
    public ResponseEntity<CustomerResponseDto> getCustomerFirstName(@PathVariable String lastName) {
        // Implementation required
        return null;
    }

    // Get by full name
    @GetMapping("/{lastName}/{firstName}")
    public ResponseEntity<CustomerResponseDto> getCustomerByFullName(@PathVariable String lastName,
                                                                     @PathVariable String firstName) {
        // Implementation required
        return null;
    }

    // Add new customer to the database
    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody AddCustomerRequestDto newCustomer) {
        // Validate the added email here
        return ResponseEntity.ok(customerDataService.createCustomer(newCustomer));
    }

    // Edit customer information
    @PatchMapping
    public ResponseEntity<CustomerResponseDto> editCustomer(@RequestBody EditCustomerRequestDto editedCustomer) {
        // Implementation required
        return null;
    }
}