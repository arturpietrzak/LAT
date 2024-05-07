package com.lat.promo.discount;

import java.math.BigDecimal;
import java.util.Currency;

public class CalculateDiscountedPriceResponseDTO {
    private BigDecimal price;
    private Currency currency;
    private String message;
    private boolean isValid;

    public CalculateDiscountedPriceResponseDTO(BigDecimal price, Currency currency, String message, boolean isValid) {
        this.price = price;
        this.currency = currency;
        this.message = message;
        this.isValid = isValid;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getMessage() {
        return message;
    }

    public boolean isValid() {
        return isValid;
    }
}
