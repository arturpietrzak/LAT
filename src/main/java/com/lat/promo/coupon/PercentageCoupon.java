package com.lat.promo.coupon;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("PERCENTAGE")
public class PercentageCoupon extends Coupon {
    @NotNull
    @Min(0)
    @Max(1)
    @Digits(integer=1, fraction=2)
    private BigDecimal discountPercentage;

    public PercentageCoupon() {
    }

    @Autowired
    public PercentageCoupon(String code, LocalDate expirationDate, int maxUsagesAmount, BigDecimal discountPercentage) {
        super(code, expirationDate, maxUsagesAmount);
        this.discountPercentage = discountPercentage;
    }

    public PercentageCoupon(Long id, String code, LocalDate expirationDate, int maxUsagesAmount, BigDecimal discountPercentage) {
        super(id, code, expirationDate, maxUsagesAmount);
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
