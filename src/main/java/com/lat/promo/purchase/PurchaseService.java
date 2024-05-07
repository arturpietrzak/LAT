package com.lat.promo.purchase;

import com.lat.promo.discount.CalculateDiscountedPriceResponseDTO;
import com.lat.promo.discount.DiscountService;
import com.lat.promo.product.Product;
import com.lat.promo.product.ProductService;
import com.lat.promo.promoCode.PromoCode;
import com.lat.promo.promoCode.PromoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    PromoCodeService promoCodeService;
    @Autowired
    ProductService productService;
    @Autowired
    DiscountService discountService;

    public int getPurchaseCountByPromoCode(String promoCode) {
        PromoCode promoCodeObject = promoCodeService.getPromoCodeByCode(promoCode).getPromoCode();

        if (promoCodeObject != null) {
            List<Purchase> purchases = purchaseRepository.findByPromoCode(promoCodeObject);

            return purchases.size();
        }

        return -1;
    }

    public Purchase processPurchase(Long productId, String promoCode) {
        Product product = productService.getProductById(productId);
        PromoCode promoCodeObject = promoCodeService.getPromoCodeByCode(promoCode).getPromoCode();
        BigDecimal discount = new BigDecimal("0.00");

        // If promoCode is null, create Purchase without any discount and return
        if (promoCode == null) {
            Purchase purchase = new Purchase(product.getCurrency(), product.getPrice(), discount, product, null);
            purchaseRepository.save(purchase);

            return purchase;
        }

        CalculateDiscountedPriceResponseDTO calculateDiscountedPriceResponseDTO = discountService.calculateDiscountedPrice(productId, promoCode);

        if (calculateDiscountedPriceResponseDTO.isValid()) {
            discount = product.getPrice().subtract(calculateDiscountedPriceResponseDTO.getPrice());
            Purchase purchase = new Purchase(product.getCurrency(), product.getPrice(), discount, product, promoCodeObject);

            purchaseRepository.save(purchase);
            return purchase;
        }

        Purchase purchase = new Purchase(product.getCurrency(), product.getPrice(), discount, product, null);
        purchaseRepository.save(purchase);

        return purchase;
    }
}
