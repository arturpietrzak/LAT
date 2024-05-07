package com.lat.promo.promoCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/promo-codes")
public class PromoCodeController {
    private final PromoCodeService promoCodeService;

    @Autowired
    public PromoCodeController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @GetMapping
    public List<PromoCode> getPromoCodes(){
        return promoCodeService.getAllPromoCodes();
    }

    @GetMapping("/{promoCode}")
    public GetPromoCodeResponseDTO getPromoCodeByCode(@PathVariable String promoCode){
        return promoCodeService.getPromoCodeByCode(promoCode);
    }

    @PostMapping
    public void addPromoCode(@RequestBody PromoCode promoCode) {
        promoCodeService.addNewPromoCode(promoCode);
    }
}
