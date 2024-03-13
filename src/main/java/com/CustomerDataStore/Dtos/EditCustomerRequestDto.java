package com.CustomerDataStore.Dtos;

import jakarta.validation.constraints.Size;

public record EditCustomerRequestDto(
        @Size(min = 3, message = "Address must be at least 3 characters")
        String address,
        @Size(min = 3, message = "Email address must be at least 3 characters")
        String emailAddress) {
}
