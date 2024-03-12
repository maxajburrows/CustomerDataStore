package com.CustomerDataStore.Controllers;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Dtos.CustomerResponseDto;
import com.CustomerDataStore.Dtos.EditCustomerRequestDto;
import com.CustomerDataStore.Services.CustomerDataService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Pre-load database with some data (4)
// TODO: Add swagger documentation (1)
@RestController
@RequestMapping("/customers")
public class CustomerDataController {

    CustomerDataService customerDataService;
    public CustomerDataController(CustomerDataService customerDataService) {
        this.customerDataService = customerDataService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        return ResponseEntity.ok(customerDataService.getCustomers());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable long customerId) {
        return ResponseEntity.ok(customerDataService.getCustomerById(customerId));
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<List<CustomerResponseDto>> getCustomerByName(
            @RequestParam(name = "first-name", required = false) String firstName,
            @RequestParam(name = "last-name", required = false) String lastName) {
        return ResponseEntity.ok(customerDataService.searchByName(firstName, lastName));
    }

    // TODO: Deal with invalid requests properly - no error message currently (2)
    // TODO: Can you make the post accept string address not just a list (3)
    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody AddCustomerRequestDto newCustomer) {
        // TODO: Validate the added email here (3)
        return ResponseEntity.ok(customerDataService.createCustomer(newCustomer));
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable long customerId,
                                                              @Valid @RequestBody EditCustomerRequestDto editedCustomer) {
        return ResponseEntity.ok(customerDataService.updateCustomer(customerId, editedCustomer));
    }
}
