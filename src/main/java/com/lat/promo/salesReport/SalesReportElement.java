package com.lat.promo.salesReport;

import java.math.BigDecimal;
import java.util.Currency;

public class SalesReportElement {
    private Currency currency;
    private BigDecimal totalRegularPrice;
    private BigDecimal totalDiscount;
    private Long numberOfPurchases;

    public SalesReportElement(Currency currency, BigDecimal totalRegularPrice, BigDecimal totalDiscount, Long numberOfPurchases) {
        this.currency = currency;
        this.totalRegularPrice = totalRegularPrice;
        this.totalDiscount = totalDiscount;
        this.numberOfPurchases = numberOfPurchases;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getTotalRegularPrice() {
        return totalRegularPrice;
    }

    public void setTotalRegularPrice(BigDecimal totalRegularPrice) {
        this.totalRegularPrice = totalRegularPrice;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Long getNumberOfPurchases() {
        return numberOfPurchases;
    }

    public void setNumberOfPurchases(Long numberOfPurchases) {
        this.numberOfPurchases = numberOfPurchases;
    }
}