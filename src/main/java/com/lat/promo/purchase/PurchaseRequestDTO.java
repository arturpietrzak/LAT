package com.lat.promo.purchase;

public class PurchaseRequestDTO {
    private Long productId;
    private String code;

    public PurchaseRequestDTO(Long productId, String code) {
        this.productId = productId;
        this.code = code;
    }

    public Long getProductId() {
        return productId;
    }

    public String getCode() {
        return code;
    }
}
