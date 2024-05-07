package com.lat.promo.coupon;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Formula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.time.LocalDate;

@Configurable
@Entity
@Inheritance
@DiscriminatorColumn(name="COUPON_TYPE")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "couponType")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "VALUE", value = ValueCoupon.class),
        @JsonSubTypes.Type(name = "PERCENTAGE", value = PercentageCoupon.class)
})
public abstract class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    @Size(min=3, max=32)
    private String code;
    private LocalDate expirationDate;
    private int maxUsages;
    @Formula(value = "(SELECT max_usages FROM coupon p WHERE p.id=id) - (SELECT COUNT(*) FROM purchase p WHERE p.coupon_id=id)")
    private int usagesLeft;

    public Coupon() {
    }

    @Autowired
    public Coupon(String code, LocalDate expirationDate, int maxUsages) {
        this.code = code;
        this.expirationDate = expirationDate;
        this.maxUsages = maxUsages;
    }

    public Coupon(Long id, String code, LocalDate expirationDate, int maxUsages) {
        this.id = id;
        this.code = code;
        this.expirationDate = expirationDate;
        this.maxUsages = maxUsages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getMaxUsages() {
        return maxUsages;
    }

    public void setMaxUsages(int maxUsages) {
        this.maxUsages = maxUsages;
    }

    public int getUsagesLeft() {
        return usagesLeft;
    }

    public void setUsagesLeft(int usagesLeft) {
        this.usagesLeft = usagesLeft;
    }
}
