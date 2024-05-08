package com.lat.promo;

import com.lat.promo.product.Product;
import com.lat.promo.product.ProductRepository;
import com.lat.promo.product.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;
    private AutoCloseable autoCloseable;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void addNewProduct_CorrectData_ReturnProduct() {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(productRepository.save(product)).thenReturn(product);

        assertThat(productService.addNewProduct(product)).isNotNull();
    }

    @Test
    void addNewProduct_IncorrectData_ReturnNull() {
        Product product = new Product(
                123L,
                null,
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(productRepository.save(product)).thenThrow(new RuntimeException("Constrain violation"));

        assertThat(productService.addNewProduct(product)).isNull();
    }

    @Test
    void updateProduct_CorrectData_ReturnProduct() {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));

        product = new Product(123L,"Producto", "Updated description", Currency.getInstance("EUR"), new BigDecimal("19.99"));
        Mockito.when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = productService.updateProduct(123L, product);

        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getDescription()).isEqualTo("Updated description");
        assertThat(updatedProduct.getName()).isEqualTo("Producto");
    }

    @Test
    void updateProduct_ProductOfThisIdDoesNotExist_ReturnNull() {
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.empty());

        Product product = new Product(123L,"Producto", "Updated description", Currency.getInstance("EUR"), new BigDecimal("19.99"));
        Product updatedProduct = productService.updateProduct(123L, product);

        assertThat(updatedProduct).isNull();
    }

    @Test
    void updateProduct_IncorrectData_ReturnNull() {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(productRepository.findById(123L)).thenReturn(Optional.of(product));
        product = new Product(123L,null, "Updated description", Currency.getInstance("EUR"), new BigDecimal("19.99"));
        Mockito.when(productRepository.save(product)).thenThrow(new RuntimeException("Constrain violation"));

        Product updatedProduct = productService.updateProduct(123L, product);

        assertThat(updatedProduct).isNull();
    }

    @Test
    void getProductById_CorrectId_ReturnProduct() {
        Product product = new Product(
                123L,
                "Product",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        assertThat(productService.getProductById(123L)).isNotNull();
    }

    @Test
    void getProductById_IncorrectId_ReturnNull() {
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThat(productService.getProductById(123L)).isNull();
    }

    @Test
    void getAllProducts_NoParameters_ReturnProducts() {
        Product product1 = new Product(
                123L,
                "Product 1",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("19.99")
        );
        Product product2 = new Product(
                1234L,
                "Product 2",
                null,
                Currency.getInstance("EUR"),
                new BigDecimal("39.99")
        );
        Mockito.when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        assertThat(productService.getAllProducts().size()).isEqualTo(2);
    }
}
