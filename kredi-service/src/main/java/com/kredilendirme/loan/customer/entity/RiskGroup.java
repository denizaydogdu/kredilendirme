package com.kredilendirme.loan.customer.entity;

public enum RiskGroup {

    LOW("Düşük riskli"),
    MEDIUM("Orta riskli"),
    HIGH("Yüksek riskli");

    private final String label;

    RiskGroup(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
