package com.lat.promo.purchase;

public class PurchaseRequestDTO {
    private Long productId;
    private String promoCode;

    public PurchaseRequestDTO(Long productId, String promoCode) {
        this.productId = productId;
        this.promoCode = promoCode;
    }

    public Long getProductId() {
        return productId;
    }

    public String getPromoCode() {
        return promoCode;
    }
}
