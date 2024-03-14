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

    // TODO: Get response with no data should return empty 200 not 204 response
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

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody AddCustomerRequestDto newCustomer) {
        return ResponseEntity.ok(customerDataService.createCustomer(newCustomer));
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable long customerId,
                                                              @Valid @RequestBody EditCustomerRequestDto editedCustomer) {
        return ResponseEntity.ok(customerDataService.updateCustomer(customerId, editedCustomer));
    }
}
