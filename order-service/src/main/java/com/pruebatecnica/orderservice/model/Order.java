package com.pruebatecnica.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Order", description = "Pedido con sus datos y líneas de artículos")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del pedido", example = "1001")
    private Long id;

    @Schema(description = "Nombre del cliente", example = "Juan Pérez")
    private String customerName;

    @Schema(description = "Email del cliente", example = "juan.perez@example.com")
    private String customerEmail;

    @Schema(description = "Fecha y hora del pedido", example = "2025-09-22T12:34:56")
    private LocalDateTime orderDate;

    @Schema(description = "Estado del pedido", example = "CONFIRMED")
    private String status;

    @Schema(description = "Importe total del pedido", example = "199.99")
    private BigDecimal totalAmount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @Schema(description = "Líneas del pedido")
    private List<OrderItem> items;
}