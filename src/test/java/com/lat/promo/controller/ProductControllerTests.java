package com.lat.promo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lat.promo.coupon.CouponService;
import com.lat.promo.product.Product;
import com.lat.promo.product.ProductController;
import com.lat.promo.product.ProductService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    void getAllProducts_NoParameters_ReturnListOfProducts() throws Exception {
        Product product1 = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Product product2 = new Product(
                1234L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(productService.getAllProducts()).thenReturn(List.of(product1, product2));
        this.mockMvc.perform(get("/api/v1/products")).andDo(print()).andExpect(status().isOk()).andExpect(content().json("[{'id':123,'name':'Product','description':null,'currency':'EUR','price':19.99},{'id':1234,'name':'Product','description':null,'currency':'EUR','price':19.99}]"));
    }

    @Test
    void addProduct_CorrectData_ReturnProduct() throws Exception {
        Product product = new Product(
                1L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(productService.addNewProduct(Mockito.any(Product.class))).thenReturn(product);
        this.mockMvc.perform(post("/api/v1/products").content("{\"name\":\"Product\",\"description\":null,\"currency\":\"EUR\",\"price\":19.99}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'name':'Product','description':null,'currency':'EUR','price':19.99}"));
    }

    @Test
    void addProduct_IncorrectData_Return400() throws Exception {
        Mockito.when(productService.addNewProduct(Mockito.any(Product.class))).thenReturn(null);
        this.mockMvc.perform(post("/api/v1/products").content("{\"description\":null,\"currency\":\"EUR\",\"price\":19.99}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProduct_CorrectData_ReturnProduct() throws Exception {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(productService.getProductById(123L)).thenReturn(product);
        this.mockMvc.perform(get("/api/v1/products/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':123,'name':'Product','description':null,'currency':'EUR','price':19.99}"));
    }

    @Test
    void getProduct_IncorrectData_Return404() throws Exception {
        Mockito.when(productService.getProductById(123L)).thenReturn(null);
        this.mockMvc.perform(get("/api/v1/products/123")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void updateProduct_CorrectData_ReturnProduct() throws Exception {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(productService.updateProduct(Mockito.any(), Mockito.any())).thenReturn(product);
        this.mockMvc
                .perform(put("/api/v1/products/123").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Product\",\"description\":null,\"currency\":\"EUR\",\"price\":19.99}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":123,\"name\":\"Product\",\"description\":null,\"currency\":\"EUR\",\"price\":19.99}"));
    }

    @Test
    void updateProduct_IncorrectData_Return400() throws Exception {
        Mockito.when(productService.updateProduct(Mockito.any(), Mockito.any())).thenReturn(null);
        this.mockMvc
                .perform(put("/api/v1/products/123").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Product\",\"description\":null}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
