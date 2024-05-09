package com.lat.promo.service;

import com.lat.promo.coupon.*;
import com.lat.promo.discount.CalculateDiscountedPriceResponseDTO;
import com.lat.promo.discount.DiscountService;
import com.lat.promo.product.Product;
import com.lat.promo.product.ProductRepository;
import com.lat.promo.product.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountServiceTests {
    @Mock
    private CouponRepository couponRepository;
    @Mock
    private ProductRepository productRepository;
    private AutoCloseable autoCloseable;
    private CouponService couponService;
    private ProductService productService;
    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
        couponService = new CouponService(couponRepository);
        discountService = new DiscountService(couponService, productService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void calculateDiscountedPrice_CorrectDataValueCoupon_ReturnDiscountedPrice() {
        Coupon coupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("15.00")
        );
        coupon.setUsagesLeft(5);
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );

        Mockito.when(couponRepository.findCouponByCode("value")).thenReturn(Optional.of(coupon));
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));

        assertThat(discountService.calculateDiscountedPrice(123L, "value")).isNotNull();
        assertThat(discountService.calculateDiscountedPrice(123L, "value").getPrice().equals(new BigDecimal("5.00"))).isTrue();
    }

    @Test
    void calculateDiscountedPrice_CorrectDataPercentageCoupon_ReturnDiscountedPrice() {
        Coupon coupon = new PercentageCoupon(
                1L,
                "percentage",
                LocalDate.of(2024, 10, 5),
                10,
                new BigDecimal("0.20")
        );
        coupon.setUsagesLeft(5);
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );

        Mockito.when(couponRepository.findCouponByCode("percentage")).thenReturn(Optional.of(coupon));
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));

        assertThat(discountService.calculateDiscountedPrice(123L, "percentage")).isNotNull();
        assertThat(discountService.calculateDiscountedPrice(123L, "percentage").getPrice().equals(new BigDecimal("16.00"))).isTrue();
    }

    @Test
    void calculateDiscountedPrice_IncorrectCode_ReturnNull() {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );

        Mockito.when(couponRepository.findCouponByCode("percentage")).thenReturn(Optional.empty());
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));

        assertThat(discountService.calculateDiscountedPrice(123L, "percentage")).isNull();
    }

    @Test
    void calculateDiscountedPrice_IncorrectProduct_ReturnNull() {
        Coupon coupon = new PercentageCoupon(
                1L,
                "percentage",
                LocalDate.of(2024, 10, 5),
                10,
                new BigDecimal("0.20")
        );
        coupon.setUsagesLeft(5);

        Mockito.when(couponRepository.findCouponByCode("percentage")).thenReturn(Optional.of(coupon));
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.empty());

        assertThat(discountService.calculateDiscountedPrice(123L, "percentage")).isNull();
    }

    @Test
    void calculateDiscountedPrice_NoUsagesLeft_ReturnRegularPriceWithMessage() {
        Coupon coupon = new PercentageCoupon(
                1L,
                "percentage",
                LocalDate.of(2024, 10, 5),
                10,
                new BigDecimal("0.20")
        );
        coupon.setUsagesLeft(0);
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );

        Mockito.when(couponRepository.findCouponByCode("percentage")).thenReturn(Optional.of(coupon));
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));

        CalculateDiscountedPriceResponseDTO responseDTO = discountService.calculateDiscountedPrice(123L, "percentage");

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getPrice().equals(new BigDecimal("20.00"))).isTrue();
        assertThat(responseDTO.getMessage()).isEqualTo("The coupon usages were exhausted.");
    }

    @Test
    void calculateDiscountedPrice_CouponExpired_ReturnRegularPriceWithMessage() {
        Coupon coupon = new PercentageCoupon(
                1L,
                "percentage",
                LocalDate.of(2010, 10, 5),
                10,
                new BigDecimal("0.20")
        );
        coupon.setUsagesLeft(10);
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );

        Mockito.when(couponRepository.findCouponByCode("percentage")).thenReturn(Optional.of(coupon));
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));

        CalculateDiscountedPriceResponseDTO responseDTO = discountService.calculateDiscountedPrice(123L, "percentage");

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getPrice().equals(new BigDecimal("20.00"))).isTrue();
        assertThat(responseDTO.getMessage()).isEqualTo("The coupon has expired.");
    }

    @Test
    void calculateDiscountedPrice_DifferentCurrencies_ReturnRegularPriceWithMessage() {
        Coupon coupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("15.00")
        );
        coupon.setUsagesLeft(10);
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("PLN"),
                new BigDecimal("20.00")
        );

        Mockito.when(couponRepository.findCouponByCode("value")).thenReturn(Optional.of(coupon));
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));

        CalculateDiscountedPriceResponseDTO responseDTO = discountService.calculateDiscountedPrice(123L, "value");

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getPrice().equals(new BigDecimal("20.00"))).isTrue();
        assertThat(responseDTO.getMessage()).isEqualTo("Currencies of the coupon and the product are different.");
    }

    @Test
    void calculateDiscountedPrice_DiscountLargerThanPrice_ReturnRegularPriceWithMessage() {
        Coupon coupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("100.00")
        );
        coupon.setUsagesLeft(10);
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );

        Mockito.when(couponRepository.findCouponByCode("value")).thenReturn(Optional.of(coupon));
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));

        CalculateDiscountedPriceResponseDTO responseDTO = discountService.calculateDiscountedPrice(123L, "value");

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getPrice().equals(new BigDecimal("0.00"))).isTrue();
    }
}
