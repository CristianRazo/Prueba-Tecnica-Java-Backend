package com.pruebatecnica.productservice.service;

import com.pruebatecnica.productservice.model.Product;
import com.pruebatecnica.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.pruebatecnica.productservice.dto.StockCheckResponse;
import com.pruebatecnica.productservice.dto.UpdateStockRequest;
import org.springframework.transaction.annotation.Transactional;
import com.pruebatecnica.productservice.dto.OrderItemDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /** Devuelve todos los productos */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /** Busca producto por ID */
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /** Crea o actualiza un producto */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /** Verifica stock por IDs */
    public List<StockCheckResponse> checkStock(List<Long> productIds) {
        Map<Long, Product> productsFound = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        return productIds.stream()
                .map(id -> {
                    Product product = productsFound.get(id);
                    boolean inStock = (product != null && product.getStock() > 0);
                    return new StockCheckResponse(id, inStock);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void reduceStock(UpdateStockRequest request) {
        for (OrderItemDTO item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalStateException(
                            "Producto con ID " + item.getProductId() + " no encontrado."));

            if (product.getStock() < item.getQuantity()) {
                throw new IllegalStateException("Stock insuficiente para el producto " + product.getName());
            }

            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }
    }

}