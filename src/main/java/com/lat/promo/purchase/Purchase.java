package com.lat.promo.purchase;

import com.lat.promo.product.Product;
import com.lat.promo.coupon.Coupon;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private LocalDate dateOfPurchase;
    @NotNull
    private Currency currency;
    @NotNull
    @Min(0)
    @Digits(integer=32, fraction=2)
    private BigDecimal price;
    @NotNull
    @Min(0)
    @Digits(integer=32, fraction=2)
    private BigDecimal discount;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Coupon coupon;

    public Purchase() {
    }

    @Autowired
    public Purchase(Currency currency, BigDecimal price, BigDecimal discount, Product product, Coupon coupon) {
        this.dateOfPurchase = LocalDate.now();
        this.currency = currency;
        this.price = price;
        this.discount = discount;
        this.product = product;
        this.coupon = coupon;
    }

    public Purchase(Long id, LocalDate dateOfPurchase, Currency currency, BigDecimal price, BigDecimal discount, Product product, Coupon coupon) {
        this.id = id;
        this.dateOfPurchase = dateOfPurchase;
        this.currency = currency;
        this.price = price;
        this.discount = discount;
        this.product = product;
        this.coupon = coupon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(@NotNull LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public @NotNull Currency getCurrency() {
        return currency;
    }

    public void setCurrency(@NotNull Currency currency) {
        this.currency = currency;
    }

    public @NotNull @Min(0) @Digits(integer = 32, fraction = 2) BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull @Min(0) @Digits(integer = 32, fraction = 2) BigDecimal price) {
        this.price = price;
    }

    public @NotNull @Min(0) @Digits(integer = 32, fraction = 2) BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(@NotNull @Min(0) @Digits(integer = 32, fraction = 2) BigDecimal discount) {
        this.discount = discount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}
