package com.lat.promo.promoCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Component
public class PromoCodeService {
    @Autowired
    PromoCodeRepository promoCodeRepository;

    public List<PromoCode> getAllPromoCodes() {
        return promoCodeRepository.findAll();
    }

    public void addNewPromoCode(PromoCode promoCode) {
        String alphanumeric_regex = "^[a-zA-Z0-9]*$";

        if (!promoCode.getCode().matches(alphanumeric_regex)) {
            return;
        }

        PromoCode promoCodeObject = promoCodeRepository.save(promoCode);
    }

    public PromoCode getPromoCodeByCode(String code) {
        Optional<PromoCode> promoCodeObject = promoCodeRepository.findPromoCodeByCode(code);

        if (promoCodeObject.isPresent()) {
            return promoCodeObject.get();
        } else {
            return null;
        }
    }
}
