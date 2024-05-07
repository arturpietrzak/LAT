package com.lat.promo.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;

    public void processPurchase(Long productId, String promoCode) {
        // Calculate discount

        // If promoCode is null, create Purchase without any discount and return

        // If promoCode is not null, check if the promo is valid - if valid, apply it, if not, treat it as if it was null
    }
}
