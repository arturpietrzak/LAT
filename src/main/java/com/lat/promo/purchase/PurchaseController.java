package com.lat.promo.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public void processPurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        purchaseService.processPurchase(
                purchaseRequestDTO.getProductId(),
                purchaseRequestDTO.getPromoCode()
        );
    }
}
