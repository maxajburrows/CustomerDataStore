package com.CustomerDataStore.Dtos;

public record CustomerDto(String firstName,
                          String lastName,
                          int age,
                          String residentialAddress,
                          String emailAddress) {
}
