package com.lat.promo.service;

import com.lat.promo.coupon.Coupon;
import com.lat.promo.coupon.CouponRepository;
import com.lat.promo.coupon.CouponService;
import com.lat.promo.coupon.ValueCoupon;
import com.lat.promo.discount.DiscountService;
import com.lat.promo.product.Product;
import com.lat.promo.product.ProductRepository;
import com.lat.promo.product.ProductService;
import com.lat.promo.purchase.Purchase;
import com.lat.promo.purchase.PurchaseRepository;
import com.lat.promo.purchase.PurchaseService;
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
import static org.mockito.AdditionalAnswers.returnsFirstArg;

public class PurchaseServiceTests {
    @Mock
    private CouponRepository couponRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private PurchaseRepository purchaseRepository;
    private AutoCloseable autoCloseable;
    private CouponService couponService;
    private ProductService productService;
    private DiscountService discountService;
    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
        couponService = new CouponService(couponRepository);
        discountService = new DiscountService(couponService, productService);
        purchaseService = new PurchaseService(purchaseRepository, couponService, productService, discountService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void processPurchase_CorrectData_ReturnPurchase() {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );
        Coupon coupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("15.00")
        );
        coupon.setUsagesLeft(5);
        Mockito.when(couponRepository.findCouponByCode("value")).thenReturn(Optional.of(coupon));
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));
        Mockito.when(purchaseRepository.save(Mockito.any(Purchase.class))).then(returnsFirstArg());;

        Purchase purchase = purchaseService.processPurchase(123L, "value");

        assertThat(purchase).isNotNull();
        assertThat(purchase.getDiscount().equals(new BigDecimal("15.00"))).isTrue();
        assertThat(purchase.getPrice().equals(new BigDecimal("20.00"))).isTrue();
        assertThat(purchase.getProduct().getId()).isEqualTo(123L);
    }

    @Test
    void processPurchase_CorrectDataPurchaseCouldNotBeSaved_ReturnNull() {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );
        Coupon coupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2024, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("15.00")
        );
        coupon.setUsagesLeft(5);
        Mockito.when(couponRepository.findCouponByCode("value")).thenReturn(Optional.of(coupon));
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));
        Mockito.when(purchaseRepository.save(Mockito.any(Purchase.class))).thenThrow(new RuntimeException("Purchase couldn't be saved."));

        Purchase purchase = purchaseService.processPurchase(123L, "value");

        assertThat(purchase).isNull();
    }

    @Test
    void processPurchase_CorrectDataNoCoupon_ReturnPurchaseWithoutDiscount() {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));
        Mockito.when(purchaseRepository.save(Mockito.any(Purchase.class))).then(returnsFirstArg());;

        Purchase purchase = purchaseService.processPurchase(123L, null);

        assertThat(purchase).isNotNull();
        assertThat(purchase.getDiscount().equals(new BigDecimal("0.00"))).isTrue();
        assertThat(purchase.getPrice().equals(new BigDecimal("20.00"))).isTrue();
        assertThat(purchase.getProduct().getId()).isEqualTo(123L);
    }

    @Test
    void processPurchase_IncorrectDataPurchaseCouldNotBeSaved_ReturnNull() {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));
        Mockito.when(purchaseRepository.save(Mockito.any(Purchase.class))).thenThrow(new RuntimeException("Purchase couldn't be saved."));

        Purchase purchase = purchaseService.processPurchase(123L, null);

        assertThat(purchase).isNull();
    }

    @Test
    void processPurchase_InvalidCoupon_ReturnPurchaseWithoutDiscount() {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );
        Coupon coupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2010, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("15.00")
        );
        coupon.setUsagesLeft(5);
        Mockito.when(couponRepository.findCouponByCode("value")).thenReturn(Optional.of(coupon));
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));
        Mockito.when(purchaseRepository.save(Mockito.any(Purchase.class))).then(returnsFirstArg());;

        Purchase purchase = purchaseService.processPurchase(123L, "value");

        assertThat(purchase).isNotNull();
        assertThat(purchase.getDiscount().equals(new BigDecimal("0.00"))).isTrue();
        assertThat(purchase.getPrice().equals(new BigDecimal("20.00"))).isTrue();
        assertThat(purchase.getProduct().getId()).isEqualTo(123L);
    }

    @Test
    void processPurchase_CouponCodeNotFound_ReturnPurchaseWithoutDiscount() {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );
        Coupon coupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2010, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("15.00")
        );
        coupon.setUsagesLeft(5);
        Mockito.when(discountService.calculateDiscountedPrice(123L, "value")).thenReturn(null);
        Mockito.when(couponRepository.findCouponByCode("value")).thenReturn(Optional.of(coupon));
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));
        Mockito.when(purchaseRepository.save(Mockito.any(Purchase.class))).then(returnsFirstArg());

        Purchase purchase = purchaseService.processPurchase(123L, "value");

        assertThat(purchase).isNotNull();
        assertThat(purchase.getDiscount().equals(new BigDecimal("0.00"))).isTrue();
        assertThat(purchase.getPrice().equals(new BigDecimal("20.00"))).isTrue();
        assertThat(purchase.getProduct().getId()).isEqualTo(123L);
    }

    @Test
    void processPurchase_InvalidCouponCouldNotBeSaved_ReturnNull() {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("20.00")
        );
        Coupon coupon = new ValueCoupon(
                1L,
                "value",
                LocalDate.of(2010, 10, 5),
                10,
                Currency.getInstance("EUR"),
                new BigDecimal("15.00")
        );
        coupon.setUsagesLeft(5);
        Mockito.when(couponRepository.findCouponByCode("value")).thenReturn(Optional.of(coupon));
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));
        Mockito.when(purchaseRepository.save(Mockito.any(Purchase.class))).thenThrow(new RuntimeException("Purchase couldn't be saved."));

        Purchase purchase = purchaseService.processPurchase(123L, "value");

        assertThat(purchase).isNull();
    }
}
