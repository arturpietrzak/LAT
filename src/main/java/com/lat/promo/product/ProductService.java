package com.lat.promo.product;

import com.lat.promo.promoCode.PromoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product product) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Product not found.");
        }

        product.setId(productId);
        return productRepository.save(product);
    }

    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Promo code not found.");
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
