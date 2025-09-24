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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Operaciones sobre productos")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Crea un producto nuevo")
    @ApiResponse(responseCode = "201", description = "Producto creado",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Product.class),
            examples = @ExampleObject(name = "Producto creado",
                value = "{\n  \"name\": \"Teclado\",\n  \"price\": 100.0,\n  \"stock\": 10,\n  \"active\": true\n}")))
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product nuevoProducto = productService.createProduct(product);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene todos los productos")
    @ApiResponse(responseCode = "200", description = "Listado de productos",
        content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Product.class))))
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productos = productService.getAllProducts();
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Busca un producto por ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Product.class),
            examples = @ExampleObject(name = "Producto encontrado",
                value = "{\n  \"id\": 1,\n  \"name\": \"Teclado\",\n  \"price\": 100.0,\n  \"stock\": 10,\n  \"active\": true\n}")))
    @ApiResponse(responseCode = "404", description = "Producto no encontrado",
        content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Verifica disponibilidad de stock")
    @ApiResponse(responseCode = "200", description = "Disponibilidad por producto",
        content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = StockCheckResponse.class))))
    @PostMapping("/check-availability")
    public ResponseEntity<List<StockCheckResponse>> checkAvailability(@RequestBody List<Long> productIds) {
        List<StockCheckResponse> stockStatus = productService.checkStock(productIds);
        return ResponseEntity.ok(stockStatus);
    }

    @Operation(summary = "Reduce stock de productos")
    @ApiResponse(responseCode = "200", description = "Stock reducido correctamente",
        content = @Content)
    @ApiResponse(responseCode = "400", description = "Solicitud inválida o stock insuficiente",
        content = @Content(mediaType = "application/json",
            examples = @ExampleObject(name = "Error",
                value = "{\n  \"status\": 400,\n  \"error\": \"Bad Request\"\n}")))
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