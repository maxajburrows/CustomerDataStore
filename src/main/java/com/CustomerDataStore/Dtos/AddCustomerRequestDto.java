package com.CustomerDataStore.Dtos;


// Validation on Dto required
public record AddCustomerRequestDto(String firstName,
                                    String lastName,
                                    int age,
                                    String Address,
                                    String emailAddress) {
}
