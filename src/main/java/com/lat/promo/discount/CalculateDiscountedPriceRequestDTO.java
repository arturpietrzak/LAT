package com.lat.promo.discount;

public class CalculateDiscountedPriceRequestDTO {
    private String code;
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
