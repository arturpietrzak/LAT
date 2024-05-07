package com.lat.promo.discount;

import com.lat.promo.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "api/v1/discount")
public class DiscountController {
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping
    public ResponseEntity<CalculateDiscountedPriceResponseDTO> calculateDiscountedPrice(@RequestBody CalculateDiscountedPriceRequestDTO calculateDiscountedPriceRequestDTO) {
        return discountService.calculateDiscountedPrice(
                calculateDiscountedPriceRequestDTO.getProductId(),
                calculateDiscountedPriceRequestDTO.getPromoCode()
        );
    }
}
