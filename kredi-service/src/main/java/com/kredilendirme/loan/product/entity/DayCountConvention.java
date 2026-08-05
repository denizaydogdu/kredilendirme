package com.kredilendirme.loan.product.entity;

import java.math.BigDecimal;

public enum DayCountConvention {

    ACT_365(365),
    ACT_360(360);

    private final int daysInYear;

    DayCountConvention(int daysInYear) {
        this.daysInYear = daysInYear;
    }

    public BigDecimal daysInYear() {
        return BigDecimal.valueOf(daysInYear);
    }
}
