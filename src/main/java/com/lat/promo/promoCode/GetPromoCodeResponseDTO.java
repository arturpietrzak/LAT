package com.lat.promo.promoCode;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class GetPromoCodeResponseDTO {
    private int usagesLeft;
    @JsonUnwrapped
    private PromoCode promoCode;

    public GetPromoCodeResponseDTO(int usagesLeft, PromoCode promoCode) {
        this.usagesLeft = usagesLeft;
        this.promoCode = promoCode;
    }

    public int getUsagesLeft() {
        return usagesLeft;
    }

    public PromoCode getPromoCode() {
        return promoCode;
    }
}
