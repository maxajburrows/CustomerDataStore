package com.CustomerDataStore.Entities;

import jakarta.persistence.*;

@Entity
@Table(name="Customer Data")
public class CustomerDataEntity {
    @Id
    @GeneratedValue
    private long customerId;
    @Column(nullable = false, length = 30)
    String firstName;
    @Column(nullable = false, length = 30)
    String lastName;
    @Column(nullable = false)
    String address;
    @Column(nullable = false, length = 100)
    String emailAddress;
}
