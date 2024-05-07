package com.lat.promo.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService; //ide po picie

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<Purchase> processPurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        return ResponseEntity.ok(
                purchaseService.processPurchase(
                    purchaseRequestDTO.getProductId(),
                    purchaseRequestDTO.getPromoCode()
                )
        );
    }
}
