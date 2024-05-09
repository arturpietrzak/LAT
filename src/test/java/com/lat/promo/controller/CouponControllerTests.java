package com.lat.promo.controller;

import com.lat.promo.coupon.*;
import com.lat.promo.product.Product;
import com.lat.promo.product.ProductController;
import com.lat.promo.product.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@WebMvcTest(CouponController.class)
public class CouponControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CouponService couponService;

    @Test
    void getAllCoupons_NoParameters_ReturnListOfCoupons() throws Exception {
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
        Mockito.when(couponService.getAllCoupons()).thenReturn(List.of(valueCoupon, percentageCoupon));
        this.mockMvc.perform(get("/api/v1/coupons")).andDo(print()).andExpect(status().isOk()).andExpect(content().json("[{\"couponType\":\"VALUE\",\"id\":1,\"code\":\"value\",\"expirationDate\":\"2024-10-05\",\"maxUsages\":10,\"usagesLeft\":0,\"currency\":\"EUR\",\"discountAmount\":19.99},{\"couponType\":\"PERCENTAGE\",\"id\":2,\"code\":\"percent\",\"expirationDate\":\"2024-10-05\",\"maxUsages\":10,\"usagesLeft\":0,\"discountPercentage\":0.05}]"));
    }

    @Test
    void addCoupon_CorrectData_ReturnCoupon() throws Exception {
        Coupon valueCoupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(couponService.addNewCoupon(Mockito.any(Coupon.class))).thenReturn(valueCoupon);
        this.mockMvc.perform(post("/api/v1/coupons").content("{\"couponType\":\"VALUE\",\"code\":\"value\",\"expirationDate\":\"2024-10-05\",\"maxUsages\":10,\"usagesLeft\":0,\"currency\":\"EUR\",\"discountAmount\":19.99}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"couponType\":\"VALUE\",\"id\":1,\"code\":\"value\",\"expirationDate\":\"2024-10-05\",\"maxUsages\":10,\"usagesLeft\":0,\"currency\":\"EUR\",\"discountAmount\":19.99}"));
    }

    @Test
    void addCoupon_IncorrectData_Return400() throws Exception {
        Mockito.when(couponService.addNewCoupon(Mockito.any(Coupon.class))).thenReturn(null);
        this.mockMvc.perform(post("/api/v1/coupons").content("{\"couponType\":\"VALUE\",\"expirationDate\":\"2024-10-05\",\"maxUsages\":10,\"usagesLeft\":0,\"currency\":\"EUR\",\"discountAmount\":19.99}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCoupon_CorrectData_ReturnCoupon() throws Exception {
        Coupon valueCoupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(couponService.getCouponByCode("value")).thenReturn(valueCoupon);
        this.mockMvc.perform(get("/api/v1/coupons/value")).andDo(print()).andExpect(status().isOk()).andExpect(content().json("{\"couponType\":\"VALUE\",\"id\":1,\"code\":\"value\",\"expirationDate\":\"2024-10-05\",\"maxUsages\":10,\"usagesLeft\":0,\"currency\":\"EUR\",\"discountAmount\":19.99}"));
    }

    @Test
    void getCoupon_IncorrectData_Return404() throws Exception {
        Mockito.when(couponService.getCouponByCode("notexisting")).thenReturn(null);
        this.mockMvc.perform(get("/api/v1/coupons/notexisting")).andDo(print()).andExpect(status().isNotFound());
    }
}
