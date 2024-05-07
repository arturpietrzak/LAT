package com.lat.promo.purchase;

import com.lat.promo.coupon.CouponService;
import com.lat.promo.discount.CalculateDiscountedPriceResponseDTO;
import com.lat.promo.discount.DiscountService;
import com.lat.promo.product.Product;
import com.lat.promo.product.ProductService;
import com.lat.promo.coupon.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    CouponService couponService;
    @Autowired
    ProductService productService;
    @Autowired
    DiscountService discountService;

    public Purchase processPurchase(Long productId, String code) {
        Product product = productService.getProductById(productId);
        Coupon couponObject = couponService.getCouponByCode(code);
        BigDecimal discount = new BigDecimal("0.00");
        Purchase purchase = null;

        // If promoCode is null, create Purchase without any discount and return
        if (code == null) {
            purchase = new Purchase(product.getCurrency(), product.getPrice(), discount, product, null);

            try {
                purchaseRepository.save(purchase);
            } catch (Exception e) {
                return null;
            }
        }

        CalculateDiscountedPriceResponseDTO calculateDiscountedPriceResponseDTO = discountService.calculateDiscountedPrice(productId, code);

        if (calculateDiscountedPriceResponseDTO.isValid()) {
            discount = product.getPrice().subtract(calculateDiscountedPriceResponseDTO.getPrice());
            purchase = new Purchase(product.getCurrency(), product.getPrice(), discount, product, couponObject);

            try {
                purchaseRepository.save(purchase);
            } catch (Exception e) {
                return null;
            }
        }

        purchase = new Purchase(product.getCurrency(), product.getPrice(), discount, product, null);

        try {
            purchaseRepository.save(purchase);
        } catch (Exception e) {
            return null;
        }

        return purchase;
    }
}
