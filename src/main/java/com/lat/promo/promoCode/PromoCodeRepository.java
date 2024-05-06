package com.lat.promo.promoCode;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {

}