package com.lat.promo.promoCode;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("PERCENTAGE")
public class PercentageCode extends PromoCode {
    private BigDecimal discountPercentage;

    public PercentageCode() {
    }

    @Autowired
    public PercentageCode(String code, LocalDate expirationDate, int maxUsagesAmount, BigDecimal discountPercentage) {
        super(code, expirationDate, maxUsagesAmount);
        this.discountPercentage = discountPercentage;
    }

    public PercentageCode(Long id, String code, LocalDate expirationDate, int maxUsagesAmount, BigDecimal discountPercentage) {
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
