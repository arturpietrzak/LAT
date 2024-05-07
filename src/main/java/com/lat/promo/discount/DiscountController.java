package com.lat.promo.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/discount")
public class DiscountController {
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping
    public ResponseEntity<CalculateDiscountedPriceResponseDTO> calculateDiscountedPrice(
            @RequestBody CalculateDiscountedPriceRequestDTO calculateDiscountedPriceRequestDTO
    ) {
        return ResponseEntity.ok(
                discountService.calculateDiscountedPrice(
                    calculateDiscountedPriceRequestDTO.getProductId(),
                    calculateDiscountedPriceRequestDTO.getPromoCode()
                )
        );
    }
}
