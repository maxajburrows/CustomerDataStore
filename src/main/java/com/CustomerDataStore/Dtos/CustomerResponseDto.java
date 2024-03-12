package com.CustomerDataStore.Dtos;

import com.CustomerDataStore.Entities.CustomerDataEntity;

import java.util.List;

public record CustomerResponseDto(
        Long customerId,
        String firstName,
        String lastName,
        int age,
        List<String> address,
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
