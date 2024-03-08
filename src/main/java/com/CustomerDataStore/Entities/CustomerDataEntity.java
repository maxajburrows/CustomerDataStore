package com.CustomerDataStore.Entities;

import jakarta.persistence.*;

@Entity
@Table(name="Customer Data")
public class CustomerDataEntity {
    @Id
    @GeneratedValue
    private long id;
    @Column
    String firstName;
    @Column
    String lastName;
    @Column
    String address;
    @Column
    String emailAddress;
}
