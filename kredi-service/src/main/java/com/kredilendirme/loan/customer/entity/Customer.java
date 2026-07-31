package com.kredilendirme.loan.customer.entity;

import com.kredilendirme.loan.common.entity.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {

    @Column(name = "customer_number", nullable = false, unique = true, length = 12)
    private String customerNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false, length = 20)
    private CustomerType customerType;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "identifier", nullable = false, unique = true, length = 11)
    private String identifier;

    @Column(name = "tax_office", length = 100)
    private String taxOffice;

    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Embedded
    private Address address;

    @Column(name = "establishment_date")
    private LocalDate establishmentDate;

    @Column(name = "exporter", nullable = false)
    private boolean exporter;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CustomerStatus status = CustomerStatus.ACTIVE;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL,
              orphanRemoval = true, fetch = FetchType.LAZY)
    private CustomerFinancialInfo financialInfo;

    protected Customer() {
    }

    public Customer(String customerNumber, CustomerType customerType, String title,
                    String identifier) {
        this.customerNumber = customerNumber;
        this.customerType = customerType;
        this.title = title;
        this.identifier = identifier;
        this.status = CustomerStatus.ACTIVE;
    }

    public void assignFinancialInfo(CustomerFinancialInfo info) {
        info.setCustomer(this);
        this.financialInfo = info;
    }

    public void deactivate() {
        this.status = CustomerStatus.PASSIVE;
    }

    public boolean isActive() {
        return status == CustomerStatus.ACTIVE;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public String getTitle() {
        return title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getTaxOffice() {
        return taxOffice;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getEstablishmentDate() {
        return establishmentDate;
    }

    public boolean isExporter() {
        return exporter;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public CustomerFinancialInfo getFinancialInfo() {
        return financialInfo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTaxOffice(String taxOffice) {
        this.taxOffice = taxOffice;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEstablishmentDate(LocalDate establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public void setExporter(boolean exporter) {
        this.exporter = exporter;
    }
}
