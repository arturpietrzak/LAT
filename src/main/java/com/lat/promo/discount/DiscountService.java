package com.lat.promo.discount;

import com.lat.promo.product.Product;
import com.lat.promo.product.ProductService;
import com.lat.promo.promoCode.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class DiscountService {
    @Autowired
    PromoCodeService promoCodeService;

    @Autowired
    ProductService productService;

    public CalculateDiscountedPriceResponseDTO calculateDiscountedPrice(
            Long productId,
            String promoCode
    ) {
        Product product = productService.getProductById(productId);
        PromoCode promoCodeObject = promoCodeService.getPromoCodeByCode(promoCode);
        LocalDate today = LocalDate.now();

        if (promoCodeObject == null) {
            throw new ResponseStatusException(NOT_FOUND, "Promo code not found.");
        }

        if (product == null) {
            throw new ResponseStatusException(NOT_FOUND, "Product not found.");
        }

        if (promoCodeObject.getUsagesLeft() <= 0) {
            return new CalculateDiscountedPriceResponseDTO(
                    product.getPrice(),
                    product.getCurrency(),
                    "The promo code usages were exhausted.",
                    false
            );
        }

        if (promoCodeObject.getExpirationDate().isBefore(today)) {
            return new CalculateDiscountedPriceResponseDTO(
                    product.getPrice(),
                    product.getCurrency(),
                    "The promo code has expired.",
                    false
            );
        }

        if (promoCodeObject instanceof ValueCode) {
            ValueCode valueCode = (ValueCode)promoCodeObject;

            if (valueCode.getCurrency().equals(product.getCurrency())) {
                BigDecimal discountedPrice = product.getPrice().subtract(valueCode.getDiscountAmount());

                if (discountedPrice.compareTo(new BigDecimal(0)) <= 0) {
                    discountedPrice = new BigDecimal(0);
                }

                return new CalculateDiscountedPriceResponseDTO(
                        discountedPrice,
                        product.getCurrency(),
                        null,
                        true
                );
            }

            return new CalculateDiscountedPriceResponseDTO(
                    product.getPrice(),
                    product.getCurrency(),
                    "Currencies of the promo code and the product are different. No discount can be applied.",
                    false
            );
        }

        PercentageCode percentageCode = (PercentageCode)promoCodeObject;
        BigDecimal percentageDiscountedPrice = product.getPrice().subtract(
                product.getPrice().multiply(percentageCode.getDiscountPercentage())
        );

        return new CalculateDiscountedPriceResponseDTO(
                percentageDiscountedPrice,
                product.getCurrency(),
                null,
                true
        );
    }
}
