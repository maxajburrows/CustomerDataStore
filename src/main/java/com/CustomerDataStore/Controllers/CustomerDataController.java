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

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        return ResponseEntity.ok(customerDataService.getCustomers());
    }

    // TODO: Add hypermedia links to responses - maybe
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable long customerId) {
        return ResponseEntity.ok(customerDataService.getCustomerById(customerId));
    }

    @GetMapping("/name/{lastName}/{firstName}")
    public ResponseEntity<CustomerResponseDto> getCustomerByFullName(@PathVariable String lastName,
                                                                     @PathVariable String firstName) {
        // Implementation required
        return null;
    }

    // TODO: Deal with invalid requests properly - no error message currently
    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody AddCustomerRequestDto newCustomer) {
        // Validate the added email here
        return ResponseEntity.ok(customerDataService.createCustomer(newCustomer));
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable long customerId,
                                                            @RequestBody EditCustomerRequestDto editedCustomer) {
        return ResponseEntity.ok(customerDataService.updateCustomer(customerId, editedCustomer));
    }
}
