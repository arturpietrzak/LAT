package com.lat.promo.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/coupons")
public class CouponController {
    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getCoupons(){
        return ResponseEntity.ok(couponService.getAllCoupons());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Coupon> getCouponByCode(@PathVariable String code){
        Coupon couponObject = couponService.getCouponByCode(code);

        if (couponObject == null) {
            throw new ResponseStatusException(NOT_FOUND, "Coupon could not be found.");
        }

        return ResponseEntity.ok(couponObject);
    }

    @PostMapping
    public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon) {
        Coupon newCoupon = couponService.addNewCoupon(coupon);

        if (newCoupon == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Couldn't create the coupon.");
        }

        return ResponseEntity.ok(couponService.addNewCoupon(coupon));
    }
}
