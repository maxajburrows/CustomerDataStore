package com.CustomerDataStore.Dtos;


// Validation on Dto required
public record CustomerDto(String firstName,
                          String lastName,
                          int age,
                          String Address,
                          String emailAddress) {
}
