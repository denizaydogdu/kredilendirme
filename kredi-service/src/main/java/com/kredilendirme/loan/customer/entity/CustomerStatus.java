package com.kredilendirme.loan.customer.entity;

public enum CustomerStatus {

    ACTIVE("Aktif"),
    PASSIVE("Pasif"),
    BLACKLISTED("Kara listede");

    private final String label;

    CustomerStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
