package com.lat.promo.promoCode;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.lat.promo.purchase.PurchaseService;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Formula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.time.LocalDate;

@Configurable
@Entity
@Inheritance
@DiscriminatorColumn(name="PROMO_TYPE")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "promoType")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "VALUE", value = ValueCode.class),
        @JsonSubTypes.Type(name = "PERCENTAGE", value = PercentageCode.class)
})
public abstract class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    @Size(min=3, max=32)
    private String code;
    private LocalDate expirationDate;
    private int maxUsages;
    @Formula(value = "(SELECT max_usages FROM promo_code p WHERE p.id=id) - (SELECT COUNT(*) FROM purchase p WHERE p.promo_code_id=id)")
    private int usagesLeft;

    public PromoCode() {
    }

    @Autowired
    public PromoCode(String code, LocalDate expirationDate, int maxUsages) {
        this.code = code;
        this.expirationDate = expirationDate;
        this.maxUsages = maxUsages;
    }

    public PromoCode(Long id, String code, LocalDate expirationDate, int maxUsages) {
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
