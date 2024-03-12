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

    // TODO: Add hypermedia links to responses - maybe
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable long customerId) {
        return ResponseEntity.ok(customerDataService.getCustomerById(customerId));
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<CustomerResponseDto> getCustomerByFullName(
            @RequestParam(name = "first-name", required = false) String firstName,
            @RequestParam(name = "last-name", required = false) String lastName) {
        System.out.println("Firstname: " + firstName);
        System.out.println("Lastname: " + lastName);
        return null;
    }

    // TODO: Deal with invalid requests properly - no error message currently
    // TODO: Can you make the post accept string address not just a list
    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody AddCustomerRequestDto newCustomer) {
        // Validate the added email here
        return ResponseEntity.ok(customerDataService.createCustomer(newCustomer));
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable long customerId,
                                                              @Valid @RequestBody EditCustomerRequestDto editedCustomer) {
        return ResponseEntity.ok(customerDataService.updateCustomer(customerId, editedCustomer));
    }
}
