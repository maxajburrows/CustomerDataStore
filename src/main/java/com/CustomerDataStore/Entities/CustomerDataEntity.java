package com.CustomerDataStore.Entities;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Repositories.CustomerDataRepository;
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
    int age;
    @Column(nullable = false)
    String address;
    @Column(nullable = false, length = 100)
    String emailAddress;

    public CustomerDataEntity() {}
    public CustomerDataEntity(AddCustomerRequestDto customer) {
        this.firstName = customer.firstName();
        this.lastName = customer.lastName();
        this.age = customer.age();
        this.address = customer.address();
        this.emailAddress = customer.emailAddress();
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
