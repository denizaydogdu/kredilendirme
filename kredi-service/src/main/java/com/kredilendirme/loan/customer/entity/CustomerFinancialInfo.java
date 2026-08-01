package com.kredilendirme.loan.customer.entity;

import com.kredilendirme.loan.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "customer_financial_info")
public class CustomerFinancialInfo extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customer customer;

    @Column(name = "annual_revenue", precision = 19, scale = 4)
    private BigDecimal annualRevenue;

    @Column(name = "total_assets", precision = 19, scale = 4)
    private BigDecimal totalAssets;

    @Column(name = "employee_count")
    private Integer employeeCount;

    @Column(name = "sector", length = 100)
    private String sector;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_group", nullable = false, length = 20)
    private RiskGroup riskGroup = RiskGroup.MEDIUM;

    @Column(name = "last_balance_sheet_date")
    private LocalDate lastBalanceSheetDate;

    protected CustomerFinancialInfo() {
    }

    public CustomerFinancialInfo(BigDecimal annualRevenue, RiskGroup riskGroup) {
        this.annualRevenue = annualRevenue;
        this.riskGroup = riskGroup;
    }

    void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getAnnualRevenue() {
        return annualRevenue;
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public String getSector() {
        return sector;
    }

    public RiskGroup getRiskGroup() {
        return riskGroup;
    }

    public LocalDate getLastBalanceSheetDate() {
        return lastBalanceSheetDate;
    }

    public void setAnnualRevenue(BigDecimal annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setRiskGroup(RiskGroup riskGroup) {
        this.riskGroup = riskGroup;
    }

    public void setLastBalanceSheetDate(LocalDate lastBalanceSheetDate) {
        this.lastBalanceSheetDate = lastBalanceSheetDate;
    }
}
