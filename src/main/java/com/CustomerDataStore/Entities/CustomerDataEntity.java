package com.CustomerDataStore.Entities;

import com.CustomerDataStore.Dtos.AddCustomerRequestDto;
import com.CustomerDataStore.Repositories.CustomerDataRepository;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Customer Data")
public class CustomerDataEntity {
    @Id
    @GeneratedValue
    private long customerId;
    @Column(nullable = false, length = 30)
    private String firstName;
    @Column(nullable = false, length = 30)
    private String lastName;
    @Column(nullable = false)
    private int age;
    /* Eager fetching prevents LazyInitialisationExceptions
    and for small datasets memory usage isn't of high concern. */
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Addresses", joinColumns = @JoinColumn(name = "customerId"))
    @Column(name = "Address", nullable = false)
    List<String> address = new ArrayList<>();
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

    public List<String> getAddress() {
        return address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
