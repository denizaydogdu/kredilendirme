package com.kredilendirme.loan.customer.dto;

import com.kredilendirme.loan.customer.entity.CustomerType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateCustomerRequest {

    @NotNull(message = "Müşteri tipi zorunludur")
    private CustomerType customerType;

    @NotBlank(message = "Unvan zorunludur")
    @Size(max = 200, message = "Unvan en fazla 200 karakter olabilir")
    private String title;

    @NotBlank(message = "Kimlik / vergi numarası zorunludur")
    @Pattern(regexp = "\\d{10,11}", message = "Kimlik / vergi numarası 10 veya 11 haneli olmalıdır")
    private String identifier;

    @Size(max = 100)
    private String taxOffice;

    @Email(message = "Geçerli bir e-posta adresi giriniz")
    @Size(max = 120)
    private String email;

    @Size(max = 20)
    private String phone;

    private String addressLine;
    private String district;
    private String city;
    private String postalCode;

    private boolean exporter;

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTaxOffice() {
        return taxOffice;
    }

    public void setTaxOffice(String taxOffice) {
        this.taxOffice = taxOffice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean isExporter() {
        return exporter;
    }

    public void setExporter(boolean exporter) {
        this.exporter = exporter;
    }
}
