package com.pruebatecnica.productservice.model;

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
public class Product {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Schema(description = "ID único del producto, generado automáticamente.", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto.", example = "Producto de Prueba")
    private String name;

    @Schema(description = "Precio del producto.", example = "10.00")
    private BigDecimal price;

    @Schema(description = "Cantidad de producto disponible en stock.", example = "100")
    private Integer stock;

    @Schema(description = "Indica si el producto está activo.", example = "true")
    private boolean active;
}