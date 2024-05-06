package com.lat.promo.product;

import com.lat.promo.promoCode.PromoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void addNewProduct(Product product) {
        Product newProduct = productRepository.save(product);
    }

    public void updateProduct(Long productId, Product product) {
        Optional<Product> productOpt = productRepository.findById(productId);

        if (productOpt.isEmpty()) {
            return;
        }

        product.setId(productId);
        productRepository.save(product);
    }

    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            return null;
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
