package com.kredilendirme.loan.product.entity;

public enum LoanType {

    CASH("Nakit Kredi"),
    NON_CASH("Gayrinakdi Kredi");

    private final String label;

    LoanType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
