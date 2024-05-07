package com.lat.promo.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class CouponService {
    @Autowired
    CouponRepository couponRepository;

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon addNewCoupon(Coupon coupon) {
        String alphanumeric_regex = "^[a-zA-Z0-9]*$";

        if (!coupon.getCode().matches(alphanumeric_regex)) {
            throw new ResponseStatusException(BAD_REQUEST, "Promo code needs to be alphanumeric.");
        }

        return couponRepository.save(coupon);
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
