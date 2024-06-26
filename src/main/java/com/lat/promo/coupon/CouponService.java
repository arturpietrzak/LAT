package com.lat.promo.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CouponService {
    @Autowired
    CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon addNewCoupon(Coupon coupon) {
        String alphanumeric_regex = "^[a-zA-Z0-9]*$";

        if (!coupon.getCode().matches(alphanumeric_regex)) {
            return null;
        }

        Coupon newCoupon = null;

        try {
            newCoupon = couponRepository.save(coupon);
        } catch (Exception e) {
            return null;
        }

        return newCoupon;
    }

    public Coupon getCouponByCode(String code) {
        Optional<Coupon> couponOptional = couponRepository.findCouponByCode(code);

        if (couponOptional.isPresent()) {
            return couponOptional.get();
        } else {
            return null;
        }
    }
}
