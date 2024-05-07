package com.lat.promo.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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
        CalculateDiscountedPriceResponseDTO response = discountService.calculateDiscountedPrice(
                calculateDiscountedPriceRequestDTO.getProductId(),
                calculateDiscountedPriceRequestDTO.getCode()
        );

        if (response == null) {
            throw new ResponseStatusException(NOT_FOUND, "Product or coupon not found.");

        }

        return ResponseEntity.ok(response);
    }
}
