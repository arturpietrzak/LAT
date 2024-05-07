package com.lat.promo.discount;

public class CalculateDiscountedPriceRequestDTO {
    private String promoCode;
    private Long productId;

    public CalculateDiscountedPriceRequestDTO(String promoCode, Long productId) {
        this.promoCode = promoCode;
        this.productId = productId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public Long getProductId() {
        return productId;
    }
}