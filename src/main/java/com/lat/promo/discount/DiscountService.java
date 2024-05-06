package com.lat.promo.discount;

import com.lat.promo.product.Product;
import com.lat.promo.product.ProductService;
import com.lat.promo.promoCode.PromoCode;
import com.lat.promo.promoCode.PromoCodeService;
import com.lat.promo.promoCode.ValueCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DiscountService {
    @Autowired
    PromoCodeService promoCodeService;

    @Autowired
    ProductService productService;

    public BigDecimal calculateDiscountedPrice(Long productId, String promoCode) {
        Product product = productService.getProductById(productId);
        PromoCode promoCodeObject = promoCodeService.getPromoCodeByCode(promoCode);

        if (promoCodeObject instanceof ValueCode) {
            ValueCode valueCode = (ValueCode)promoCodeObject;

            if (valueCode.getCurrency().equals(product.getCurrency())) {
                return product.getPrice().subtract(valueCode.getDiscountAmount());
            }

            return product.getPrice();
        }

        return product.getPrice();
    }
}
