package com.kredilendirme.loan.customer.dto;

import com.kredilendirme.loan.customer.entity.Customer;

import java.time.LocalDateTime;

public class CustomerResponse {

    private final Long id;
    private final String customerNumber;
    private final String customerType;
    private final String title;
    private final String identifier;
    private final String email;
    private final String phone;
    private final String city;
    private final boolean exporter;
    private final String status;
    private final LocalDateTime createdAt;

    private CustomerResponse(Long id, String customerNumber, String customerType,
                             String title, String identifier, String email, String phone,
                             String city, boolean exporter, String status,
                             LocalDateTime createdAt) {
        this.id = id;
        this.customerNumber = customerNumber;
        this.customerType = customerType;
        this.title = title;
        this.identifier = identifier;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.exporter = exporter;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getCustomerNumber(),
                customer.getCustomerType().name(),
                customer.getTitle(),
                customer.getIdentifier(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress() != null ? customer.getAddress().getCity() : null,
                customer.isExporter(),
                customer.getStatus().name(),
                customer.getCreatedAt());
    }

    public Long getId() {
        return id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getTitle() {
        return title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public boolean isExporter() {
        return exporter;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
