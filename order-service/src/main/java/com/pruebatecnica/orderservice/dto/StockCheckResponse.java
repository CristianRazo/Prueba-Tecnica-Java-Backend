package com.pruebatecnica.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "StockCheckResponse", description = "Disponibilidad de stock por producto")
public class StockCheckResponse {
    @Schema(description = "ID del producto", example = "1")
    private Long productId;
    @Schema(description = "Indica si hay stock disponible", example = "true")
    private boolean isInStock;
}