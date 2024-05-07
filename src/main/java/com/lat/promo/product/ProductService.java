package com.lat.promo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
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

        Product updatedProduct = null;

        product.setId(productId);

        try {
            updatedProduct = productRepository.save(product);
        } catch (Exception e) {
            throw new ResponseStatusException(BAD_REQUEST, "The product couldn't be updated.");
        }

        return updatedProduct;
    }

    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Product not found.");
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
