package com.CustomerDataStore.Controllers;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.CustomerDataStore.Dtos.EditCustomerRequestDto;
import com.CustomerDataStore.Entities.CustomerDataEntity;
import com.CustomerDataStore.Services.CustomerDataStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerDataStoreController {

    CustomerDataStoreService customerDataStoreService;
    public CustomerDataStoreController(CustomerDataStoreService customerDataStoreService) {
        this.customerDataStoreService = customerDataStoreService;
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
        // Implementation required
        return null;
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

    // Add customer
    @PostMapping
    public ResponseEntity<CustomerResponseDto> addNewCustomer(@RequestBody AddCustomerRequestDto newCustomer) {
        // Validate the added email here
        CustomerResponseDto newCustomerDto = customerDataStoreService.createCustomer(newCustomer);
        return ResponseEntity.ok(newCustomerDto);
    }

    // Edit customer information
    @PatchMapping
    public ResponseEntity<CustomerResponseDto> editCustomer(@RequestBody EditCustomerRequestDto editedCustomer) {
        // Implementation required
        return null;
    }
}
