package com.lat.promo.discount;

import jakarta.validation.constraints.NotNull;

public class CalculateDiscountedPriceRequestDTO {
    @NotNull
    private String code;
    @NotNull
    private Long productId;

    public CalculateDiscountedPriceRequestDTO(String code, Long productId) {
        this.code = code;
        this.productId = productId;
    }

    public String getCode() {
        return code;
    }

    public Long getProductId() {
        return productId;
    }
}
