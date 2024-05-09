package com.lat.promo.controller;

import com.lat.promo.coupon.*;
import com.lat.promo.discount.CalculateDiscountedPriceRequestDTO;
import com.lat.promo.discount.CalculateDiscountedPriceResponseDTO;
import com.lat.promo.discount.DiscountController;
import com.lat.promo.discount.DiscountService;
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

@WebMvcTest(DiscountController.class)
public class DiscountControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DiscountService discountService;

    @Test
    void calculateDiscountedPrice_CorrectData_ReturnDiscount() throws Exception {
        CalculateDiscountedPriceResponseDTO responseDTO = new CalculateDiscountedPriceResponseDTO(
                new BigDecimal("20.00"),
                Currency.getInstance("EUR"),
                null,
                true
        );
        Mockito.when(discountService.calculateDiscountedPrice(123L, "value")).thenReturn(responseDTO);
        this.mockMvc.perform(post("/api/v1/discount").content("{\"code\": \"value\",\n \"productId\": 123}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().json("{\"price\":20.00,\"currency\":\"EUR\",\"message\":null,\"valid\":true}"));
    }

    @Test
    void calculateDiscountedPrice_IncorrectData_Return404() throws Exception {
        CalculateDiscountedPriceResponseDTO responseDTO = new CalculateDiscountedPriceResponseDTO(
                new BigDecimal("20.00"),
                Currency.getInstance("EUR"),
                null,
                true
        );
        Mockito.when(discountService.calculateDiscountedPrice(123L, "value")).thenReturn(responseDTO);
        this.mockMvc.perform(post("/api/v1/discount").content("{\"code\": \"notexisting\",\n \"productId\": 1000}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound());
    }
}
