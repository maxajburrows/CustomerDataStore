package com.CustomerDataStore.Dtos;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
        @Min(value = 10, message = "Customer must be at least 10 years old!")
        @Max(value = 110, message = "Customer must be no older than 110!")
        int age,
        @NotNull(message = "Customer must have at least one address!")
        @Size(min = 1, message = "Customer must have at least one address!")
        List<String> address,
        @NotNull(message = "Customer must have an email address!")
        @Size(min = 3, message = "Email address must be at least 3 characters")
        String emailAddress) {
}
