package com.lat.promo.promoCode;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Entity
@DiscriminatorValue("VALUE")
public class ValueCode extends PromoCode{
    private Currency currency;
    private BigDecimal discountAmount;

    public ValueCode() {
    }

    @Autowired
    public ValueCode(String code, LocalDate expirationDate, int maxUsagesAmount, Currency currency, BigDecimal discountAmount) {
        super(code, expirationDate, maxUsagesAmount);
        this.currency = currency;
        this.discountAmount = discountAmount;
    }

    public ValueCode(Long id, String code, LocalDate expirationDate, int maxUsagesAmount, Currency currency, BigDecimal discountAmount) {
        super(id, code, expirationDate, maxUsagesAmount);
        this.currency = currency;
        this.discountAmount = discountAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
}
