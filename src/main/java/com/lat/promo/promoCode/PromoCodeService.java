package com.lat.promo.promoCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class PromoCodeService {
    @Autowired
    PromoCodeRepository promoCodeRepository;

    public List<PromoCode> getAllPromoCodes() {
        return promoCodeRepository.findAll();
    }

    public PromoCode addNewPromoCode(PromoCode promoCode) {
        String alphanumeric_regex = "^[a-zA-Z0-9]*$";

        if (!promoCode.getCode().matches(alphanumeric_regex)) {
            throw new ResponseStatusException(BAD_REQUEST, "Promo code needs to be alphanumeric.");
        }

        return promoCodeRepository.save(promoCode);
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
