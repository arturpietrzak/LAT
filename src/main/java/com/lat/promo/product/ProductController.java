package com.lat.promo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProducts(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);

        if (product == null) {
            throw new ResponseStatusException(NOT_FOUND, "The product couldn't be found.");
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = productService.addNewProduct(product);

        if (newProduct == null) {
            throw new ResponseStatusException(BAD_REQUEST, "The product couldn't be created.");
        }

        return ResponseEntity.ok(newProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(productId, product);

        if (updatedProduct == null) {
            throw new ResponseStatusException(BAD_REQUEST, "The product couldn't be updated.");
        }

        return ResponseEntity.ok(updatedProduct);
    }
}
