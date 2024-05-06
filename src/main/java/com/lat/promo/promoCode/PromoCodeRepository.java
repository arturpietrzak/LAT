package com.lat.promo.promoCode;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
    @Query("SELECT pc FROM PromoCode pc WHERE pc.code = ?1")
    Optional<PromoCode> findPromoCodeByCode(String code);
}