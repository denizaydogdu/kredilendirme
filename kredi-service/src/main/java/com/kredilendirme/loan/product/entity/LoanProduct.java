package com.kredilendirme.loan.product.entity;

import com.kredilendirme.loan.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "loan_product")
public class LoanProduct extends BaseEntity {

    @Column(name = "code", nullable = false, unique = true, length = 40)
    private String code;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type", nullable = false, length = 20)
    private LoanType loanType;

    @Enumerated(EnumType.STRING)
    @Column(name = "calculation_method", nullable = false, length = 20)
    private CalculationMethod calculationMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_count", nullable = false, length = 10)
    private DayCountConvention dayCountConvention;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "min_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal minAmount;

    @Column(name = "max_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal maxAmount;

    @Column(name = "min_term_months")
    private Integer minTermMonths;

    @Column(name = "max_term_months")
    private Integer maxTermMonths;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    protected LoanProduct() {
    }

    public boolean supportsAmount(BigDecimal amount) {
        return amount.compareTo(minAmount) >= 0 && amount.compareTo(maxAmount) <= 0;
    }

    public boolean supportsTerm(Integer termMonths) {
        if (minTermMonths == null || maxTermMonths == null) {
            return termMonths == null;
        }
        return termMonths != null
                && termMonths >= minTermMonths
                && termMonths <= maxTermMonths;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public CalculationMethod getCalculationMethod() {
        return calculationMethod;
    }

    public DayCountConvention getDayCountConvention() {
        return dayCountConvention;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public Integer getMinTermMonths() {
        return minTermMonths;
    }

    public Integer getMaxTermMonths() {
        return maxTermMonths;
    }

    public boolean isActive() {
        return active;
    }
}
