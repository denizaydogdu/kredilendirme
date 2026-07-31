package com.kredilendirme.loan.customer.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(name = "address_line", length = 255)
    private String addressLine;

    @Column(name = "district", length = 60)
    private String district;

    @Column(name = "city", length = 60)
    private String city;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    protected Address() {
    }

    public Address(String addressLine, String district, String city, String postalCode) {
        this.addressLine = addressLine;
        this.district = district;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
