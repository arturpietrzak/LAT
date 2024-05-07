package com.lat.promo.purchase;

import com.lat.promo.product.Product;
import com.lat.promo.promoCode.PromoCode;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate dateOfPurchase;
    private Currency currency;
    private BigDecimal price;
    private BigDecimal discount;
    @ManyToOne
    private Product product;
    @ManyToOne
    private PromoCode promoCode;

    public Purchase() {
    }

    @Autowired
    public Purchase(Currency currency, BigDecimal price, BigDecimal discount, Product product, PromoCode promoCode) {
        this.dateOfPurchase = LocalDate.now();
        this.currency = currency;
        this.price = price;
        this.discount = discount;
        this.product = product;
        this.promoCode = promoCode;
    }

    public Purchase(Long id, LocalDate dateOfPurchase, Currency currency, BigDecimal price, BigDecimal discount, Product product, PromoCode promoCode) {
        this.id = id;
        this.dateOfPurchase = dateOfPurchase;
        this.currency = currency;
        this.price = price;
        this.discount = discount;
        this.product = product;
        this.promoCode = promoCode;
    }

    public PromoCode getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(PromoCode promoCode) {
        this.promoCode = promoCode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
