package com.CustomerDataStore.Dtos;

public record CustomerResponseDto(
        Long customerId,
        String firstName,
        String lastName,
        int age,
        String Address,
        String emailAddress) {
}
