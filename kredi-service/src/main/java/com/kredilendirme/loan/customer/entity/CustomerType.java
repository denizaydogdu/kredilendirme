package com.kredilendirme.loan.customer.entity;

public enum CustomerType {

    INDIVIDUAL("Bireysel", 11),
    SME("KOBİ", 10),
    CORPORATE("Kurumsal", 10);

    private final String label;
    private final int identifierLength;

    CustomerType(String label, int identifierLength) {
        this.label = label;
        this.identifierLength = identifierLength;
    }

    public String getLabel() {
        return label;
    }

    public int getIdentifierLength() {
        return identifierLength;
    }

    public boolean isLegalEntity() {
        return this != INDIVIDUAL;
    }
}
