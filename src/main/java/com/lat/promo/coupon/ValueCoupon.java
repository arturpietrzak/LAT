package com.lat.promo.coupon;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Entity
@DiscriminatorValue("VALUE")
public class ValueCoupon extends Coupon {
    private Currency currency;
    private BigDecimal discountAmount;

    public ValueCoupon() {
    }

    @Autowired
    public ValueCoupon(String code, LocalDate expirationDate, int maxUsagesAmount, Currency currency, BigDecimal discountAmount) {
        super(code, expirationDate, maxUsagesAmount);
        this.currency = currency;
        this.discountAmount = discountAmount;
    }

    public ValueCoupon(Long id, String code, LocalDate expirationDate, int maxUsagesAmount, Currency currency, BigDecimal discountAmount) {
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
