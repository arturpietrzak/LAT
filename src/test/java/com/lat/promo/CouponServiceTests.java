package com.lat.promo;

import com.lat.promo.coupon.*;
import com.lat.promo.product.Product;
import com.lat.promo.product.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ActiveProfiles("test")
@SpringBootTest
public class CouponServiceTests {
    @Mock
    private CouponRepository couponRepository;
    private AutoCloseable autoCloseable;
    private CouponService couponService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        couponService = new CouponService(couponRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllCoupons_NoParameters_ReturnCoupons() {
        Coupon valueCoupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Coupon percentageCoupon = new PercentageCoupon(
                2L,
                "percent",
                LocalDate.of(2024, 10, 5),
                10,
                new BigDecimal("0.05")
        );

        Mockito.when(couponRepository.findAll()).thenReturn(List.of(valueCoupon, percentageCoupon));

        assertThat(couponService.getAllCoupons().size()).isEqualTo(2);
    }

    @Test
    void addNewCoupon_CorrectData_ReturnCoupon() {
        Coupon valueCoupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(couponRepository.save(valueCoupon)).thenReturn(valueCoupon);

        assertThat(couponService.addNewCoupon(valueCoupon)).isNotNull();
    }

    @Test
    void addNewCoupon_IncorrectData_ReturnNull() {
        Coupon valueCoupon = new ValueCoupon(
                1L,
                "dsa",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                null
        );
        Mockito.when(couponRepository.save(valueCoupon)).thenThrow(new RuntimeException("Constrain violation"));

        assertThat(couponService.addNewCoupon(valueCoupon)).isNull();
    }

    @Test
    void addNewCoupon_NotAlphanumericCode_ReturnNull() {
        Coupon valueCoupon = new ValueCoupon(
                1L,
                "こんにちはお元気ですか",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );

        assertThat(couponService.addNewCoupon(valueCoupon)).isNull();
    }

    @Test
    void getCouponByCode_CorrectCode_ReturnCoupon() {
        Coupon valueCoupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(couponRepository.findCouponByCode("value")).thenReturn(Optional.of(valueCoupon));

        assertThat(couponService.getCouponByCode("value")).isNotNull();
    }

    @Test
    void getCouponByCode_IncorrectCode_ReturnNull() {
        Mockito.when(couponRepository.findCouponByCode(anyString())).thenReturn(Optional.empty());

        assertThat(couponService.getCouponByCode("value")).isNull();
    }
}
