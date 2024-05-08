package com.lat.promo;

import com.lat.promo.coupon.CouponRepository;
import com.lat.promo.coupon.CouponService;
import com.lat.promo.discount.DiscountService;
import com.lat.promo.product.ProductRepository;
import com.lat.promo.product.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

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
}
