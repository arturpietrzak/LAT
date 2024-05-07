package com.lat.promo.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping(path = "api/v1/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<Purchase> processPurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        Purchase purchase = purchaseService.processPurchase(
                purchaseRequestDTO.getProductId(),
                purchaseRequestDTO.getCode()
        );

        if (purchase == null) {
            throw new ResponseStatusException(BAD_REQUEST, "The purchase couldn't be performed.");
        }

        return ResponseEntity.ok(purchase);
    }
}
