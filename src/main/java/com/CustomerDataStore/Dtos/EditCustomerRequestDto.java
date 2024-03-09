package com.CustomerDataStore.Dtos;


// Validation on Dto required
public record EditCustomerRequestDto(
        String firstName,
        String lastName,
        int age,
        String address,
        String emailAddress) {
}
