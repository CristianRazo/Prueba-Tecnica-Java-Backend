package com.pruebatecnica.orderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "OrderItem", description = "Línea de pedido con producto y cantidad")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la línea de pedido", example = "10")
    private Long id;

    @Schema(description = "ID del producto", example = "1")
    private Long productId;

    @Schema(description = "Cantidad solicitada", example = "2")
    private Integer quantity;

    @Schema(description = "Precio unitario al momento del pedido", example = "99.99")
    private BigDecimal unitPrice;
}