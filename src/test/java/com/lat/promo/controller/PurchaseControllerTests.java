package com.lat.promo.controller;

import com.lat.promo.coupon.Coupon;
import com.lat.promo.coupon.ValueCoupon;
import com.lat.promo.discount.CalculateDiscountedPriceResponseDTO;
import com.lat.promo.product.Product;
import com.lat.promo.purchase.Purchase;
import com.lat.promo.purchase.PurchaseController;
import com.lat.promo.purchase.PurchaseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseController.class)
public class PurchaseControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PurchaseService purchaseService;

    @Test
    void processPurchase_CorrectData_ReturnPurchase() throws Exception {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Coupon valueCoupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("4.99")
        );
        Purchase purchase = new Purchase(123L,
                LocalDate.of(2023, 10, 10),
                Currency.getInstance("EUR"),
                new BigDecimal("15.00"),
                new BigDecimal("4.99"),
                product,
                valueCoupon
        );
        Mockito.when(purchaseService.processPurchase(123L, "value")).thenReturn(purchase);

        this.mockMvc.perform(post("/api/v1/purchase").content("{\"code\": \"value\",\n \"productId\": 123}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":123,\"dateOfPurchase\":\"2023-10-10\",\"currency\":\"EUR\",\"price\":15.00,\"discount\":4.99,\"product\":{\"id\":123,\"name\":\"Product\",\"description\":null,\"currency\":\"EUR\",\"price\":19.99},\"coupon\":{\"couponType\":\"VALUE\",\"id\":1,\"code\":\"value\",\"expirationDate\":\"2024-10-05\",\"maxUsages\":10,\"usagesLeft\":0,\"currency\":\"EUR\",\"discountAmount\":4.99}}"));
    }

    @Test
    void processPurchase_IncorrectData_Return400() throws Exception {
        Mockito.when(purchaseService.processPurchase(321L, "notexisting")).thenReturn(null);

        this.mockMvc.perform(post("/api/v1/purchase").content("{\"code\": \"notexisting\",\n \"productId\": 321}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
