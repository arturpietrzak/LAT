package com.lat.promo.promoCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/promo-codes")
public class PromoCodeController {
    private final PromoCodeService promoCodeService;

    @Autowired
    public PromoCodeController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @GetMapping
    public ResponseEntity<List<PromoCode>> getPromoCodes(){
        return ResponseEntity.ok(promoCodeService.getAllPromoCodes());
    }

    @GetMapping("/{promoCode}")
    public ResponseEntity<PromoCode> getPromoCodeByCode(@PathVariable String promoCode){
        PromoCode promoCodeObject = promoCodeService.getPromoCodeByCode(promoCode);

        if (promoCodeObject == null) {
            throw new ResponseStatusException(NOT_FOUND, "Promo code could not be found.");
        }

        return ResponseEntity.ok(promoCodeObject);
    }

    @PostMapping
    public ResponseEntity<PromoCode> addPromoCode(@RequestBody PromoCode promoCode) {
        return ResponseEntity.ok(promoCodeService.addNewPromoCode(promoCode));
    }
}
