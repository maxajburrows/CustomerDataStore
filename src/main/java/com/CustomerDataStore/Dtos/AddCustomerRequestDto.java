package com.CustomerDataStore.Dtos;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

// Validation on Dto required
public record AddCustomerRequestDto(
        @NotNull(message = "Customer must have a first name!")
        @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
        String firstName,
        @NotNull(message = "Customer must have a last name!")
        @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
        String lastName,
        @NotNull(message = "Customer must have an age")
        int age,
        @NotNull(message = "Customer must have at least one address")
        @Size(min = 3, message = "Address must be at least 3 characters")
        List<String> address,
        @NotNull(message = "Customer must have an email address!")
        @Size(min = 3, message = "Email address must be at least 3 characters")
        String emailAddress) {
}
