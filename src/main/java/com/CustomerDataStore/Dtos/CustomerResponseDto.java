package com.CustomerDataStore.Dtos;

import com.CustomerDataStore.Entities.CustomerDataEntity;

public record CustomerResponseDto(
        Long customerId,
        String firstName,
        String lastName,
        int age,
        String address,
        String emailAddress) {
    public CustomerResponseDto(CustomerDataEntity customer) {
        this(customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAge(),
                customer.getAddress(),
                customer.getEmailAddress());
    }
}
