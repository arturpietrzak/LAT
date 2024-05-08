package com.lat.promo.discount;

import com.lat.promo.product.Product;
import com.lat.promo.product.ProductService;
import com.lat.promo.coupon.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class DiscountService {
    @Autowired
    CouponService couponService;
    @Autowired
    ProductService productService;

    public DiscountService(CouponService couponService, ProductService productService) {
        this.couponService = couponService;
        this.productService = productService;
    }

    public CalculateDiscountedPriceResponseDTO calculateDiscountedPrice(
            Long productId,
            String code
    ) {
        Product product = productService.getProductById(productId);
        Coupon couponObject = couponService.getCouponByCode(code);
        LocalDate today = LocalDate.now();

        if (couponObject == null || product == null) {
            return null;
        }

        if (couponObject.getUsagesLeft() <= 0) {
            return new CalculateDiscountedPriceResponseDTO(
                    product.getPrice(),
                    product.getCurrency(),
                    "The coupon usages were exhausted.",
                    false
            );
        }

        if (couponObject.getExpirationDate().isBefore(today)) {
            return new CalculateDiscountedPriceResponseDTO(
                    product.getPrice(),
                    product.getCurrency(),
                    "The coupon has expired.",
                    false
            );
        }

        if (couponObject instanceof ValueCoupon) {
            ValueCoupon valueCoupon = (ValueCoupon) couponObject;

            if (valueCoupon.getCurrency().equals(product.getCurrency())) {
                BigDecimal discountedPrice = product.getPrice().subtract(valueCoupon.getDiscountAmount());

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
                    "Currencies of the coupon and the product are different.",
                    false
            );
        }

        PercentageCoupon percentageCoupon = (PercentageCoupon) couponObject;
        BigDecimal percentageDiscountedPrice = product.getPrice().subtract(
                product.getPrice().multiply(percentageCoupon.getDiscountPercentage())
        );

        return new CalculateDiscountedPriceResponseDTO(
                percentageDiscountedPrice,
                product.getCurrency(),
                null,
                true
        );
    }
}
