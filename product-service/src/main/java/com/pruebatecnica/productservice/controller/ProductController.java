package com.pruebatecnica.productservice.controller;

import com.pruebatecnica.productservice.dto.StockCheckResponse;
import com.pruebatecnica.productservice.dto.UpdateStockRequest;
import com.pruebatecnica.productservice.model.Product;
import com.pruebatecnica.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /** Crea un producto nuevo */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product nuevoProducto = productService.createProduct(product);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    /** Obtiene todos los productos */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productos = productService.getAllProducts();
        return ResponseEntity.ok(productos);
    }

    /** Busca un producto por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Verifica disponibilidad de stock */
    @PostMapping("/check-availability")
    public ResponseEntity<List<StockCheckResponse>> checkAvailability(@RequestBody List<Long> productIds) {
        List<StockCheckResponse> stockStatus = productService.checkStock(productIds);
        return ResponseEntity.ok(stockStatus);
    }

    /** Reduce stock de productos */
    @PostMapping("/reduce-stock")
    public ResponseEntity<Void> reduceStock(@RequestBody UpdateStockRequest request) {
        try {
            productService.reduceStock(request);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}