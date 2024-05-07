package com.lat.promo.discount;

import com.lat.promo.product.Product;
import com.lat.promo.product.ProductService;
import com.lat.promo.promoCode.PercentageCode;
import com.lat.promo.promoCode.PromoCode;
import com.lat.promo.promoCode.PromoCodeService;
import com.lat.promo.promoCode.ValueCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DiscountService {
    @Autowired
    PromoCodeService promoCodeService;

    @Autowired
    ProductService productService;

    public ResponseEntity<CalculateDiscountedPriceResponseDTO> calculateDiscountedPrice(
            Long productId,
            String promoCode
    ) {
        Product product = productService.getProductById(productId);
        PromoCode promoCodeObject = promoCodeService.getPromoCodeByCode(promoCode);
        LocalDate today = LocalDate.now();

        if (promoCodeObject.getExpirationDate().isBefore(today)) {
            return new ResponseEntity<>(
                    new CalculateDiscountedPriceResponseDTO(
                            product.getPrice(),
                            product.getCurrency(),
                            "The promo code has expired."
                    ),
                    HttpStatus.OK
            );
        }

        if (promoCodeObject instanceof ValueCode) {
            ValueCode valueCode = (ValueCode)promoCodeObject;

            if (valueCode.getCurrency().equals(product.getCurrency())) {
                BigDecimal discountedPrice = product.getPrice().subtract(valueCode.getDiscountAmount());

                if (discountedPrice.compareTo(new BigDecimal(0)) <= 0) {
                    discountedPrice = new BigDecimal(0);
                }

                return new ResponseEntity<>(
                        new CalculateDiscountedPriceResponseDTO(
                                discountedPrice,
                                product.getCurrency(),
                                null
                        ),
                        HttpStatus.OK
                );
            }

            return new ResponseEntity<>(
                    new CalculateDiscountedPriceResponseDTO(
                            product.getPrice(),
                            product.getCurrency(),
                            "Currencies of the promo code and the product are different. No discount can be applied."
                    ),
                    HttpStatus.OK
            );
        }

        PercentageCode percentageCode = (PercentageCode)promoCodeObject;
        BigDecimal percentageDiscountedPrice = product.getPrice().subtract(
                product.getPrice().multiply(percentageCode.getDiscountPercentage())
        );

        return new ResponseEntity<>(
                new CalculateDiscountedPriceResponseDTO(
                        percentageDiscountedPrice,
                        product.getCurrency(),
                        null
                ),
                HttpStatus.OK
        );
    }
}
