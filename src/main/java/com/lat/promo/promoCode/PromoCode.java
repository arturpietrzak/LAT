package com.lat.promo.promoCode;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.lat.promo.purchase.PurchaseService;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.time.LocalDate;

@Configurable
@Entity
@Inheritance
@DiscriminatorColumn(name="PROMO_TYPE")
@Table(name="PromoCodes")
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
    private int maxUsagesAmount;


    public PromoCode() {
    }

    @Autowired
    public PromoCode(String code, LocalDate expirationDate, int maxUsagesAmount) {
        this.code = code;
        this.expirationDate = expirationDate;
        this.maxUsagesAmount = maxUsagesAmount;
    }

    public PromoCode(Long id, String code, LocalDate expirationDate, int maxUsagesAmount) {
        this.id = id;
        this.code = code;
        this.expirationDate = expirationDate;
        this.maxUsagesAmount = maxUsagesAmount;
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

    public int getMaxUsagesAmount() {
        return maxUsagesAmount;
    }

    public void setMaxUsagesAmount(int maxUsagesAmount) {
        this.maxUsagesAmount = maxUsagesAmount;
    }
}
