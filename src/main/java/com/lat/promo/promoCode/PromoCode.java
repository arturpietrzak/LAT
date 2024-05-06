package com.lat.promo.promoCode;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

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
    private int usagesAmountLeft;

    public PromoCode() {
    }

    @Autowired
    public PromoCode(String code, LocalDate expirationDate, int maxUsagesAmount, int usagesAmountLeft) {
        this.code = code;
        this.expirationDate = expirationDate;
        this.maxUsagesAmount = maxUsagesAmount;
        this.usagesAmountLeft = usagesAmountLeft;
    }

    public PromoCode(Long id, String code, LocalDate expirationDate, int maxUsagesAmount, int usagesAmountLeft) {
        this.id = id;
        this.code = code;
        this.expirationDate = expirationDate;
        this.maxUsagesAmount = maxUsagesAmount;
        this.usagesAmountLeft = usagesAmountLeft;
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

    public int getUsagesAmountLeft() {
        return usagesAmountLeft;
    }

    public void setUsagesAmountLeft(int usagesAmountLeft) {
        this.usagesAmountLeft = usagesAmountLeft;
    }
}
