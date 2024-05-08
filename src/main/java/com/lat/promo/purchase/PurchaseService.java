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
import java.math.RoundingMode;

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

    public PurchaseService(
            PurchaseRepository purchaseRepository,
            CouponService couponService,
            ProductService productService,
            DiscountService discountService
    ) {
        this.purchaseRepository = purchaseRepository;
        this.couponService = couponService;
        this.productService = productService;
        this.discountService = discountService;
    }

    public Purchase processPurchase(Long productId, String code) {
        Product product = productService.getProductById(productId);
        BigDecimal discount = new BigDecimal("0.00");
        Purchase purchase = null;

        // If code is null, create Purchase without any discount and return
        if (code == null) {
            purchase = new Purchase(product.getCurrency(), product.getPrice(), discount, product, null);

            try {
                return purchaseRepository.save(purchase);
            } catch (Exception e) {
                return null;
            }
        }

        CalculateDiscountedPriceResponseDTO calculateDiscountedPriceResponseDTO = discountService.calculateDiscountedPrice(productId, code);
        Coupon coupon = couponService.getCouponByCode(code);

        if (calculateDiscountedPriceResponseDTO.isValid()) {
            discount = product.getPrice().subtract(calculateDiscountedPriceResponseDTO.getPrice()).setScale(2, RoundingMode.FLOOR);
            purchase = new Purchase(product.getCurrency(), product.getPrice(), discount, product, coupon);

            try {
                return purchaseRepository.save(purchase);
            } catch (Exception e) {
                return null;
            }
        }

        purchase = new Purchase(product.getCurrency(), product.getPrice(), discount, product, null);

        try {
            return purchaseRepository.save(purchase);
        } catch (Exception e) {
            return null;
        }
    }
}
