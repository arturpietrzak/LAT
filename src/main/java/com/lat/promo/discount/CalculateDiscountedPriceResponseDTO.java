package com.lat.promo.discount;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Currency;

public class CalculateDiscountedPriceResponseDTO {
    @NotNull
    private BigDecimal price;
    @NotNull
    private Currency currency;
    private String message;
    @NotNull
    private boolean isValid;

    public CalculateDiscountedPriceResponseDTO(BigDecimal price, Currency currency, String message, boolean isValid) {
        this.price = price;
        this.currency = currency;
        this.message = message;
        this.isValid = isValid;
    }

    public @NotNull BigDecimal getPrice() {
        return price;
    }

    public @NotNull Currency getCurrency() {
        return currency;
    }

    public String getMessage() {
        return message;
    }

    @NotNull
    public boolean isValid() {
        return isValid;
    }
}
