package com.CustomerDataStore.Dtos;

public record CustomerResponseDto(String firstName,
                                  String lastName,
                                  int age,
                                  String Address,
                                  String emailAddress) {
}
