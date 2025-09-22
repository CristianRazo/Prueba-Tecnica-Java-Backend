package com.pruebatecnica.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "StockCheckResponse", description = "Resultado de disponibilidad de stock")
public class StockCheckResponse {
    @Schema(description = "ID del producto", example = "1")
    private Long productId;
    @Schema(description = "Si el producto tiene stock", example = "true")
    private boolean isInStock;
}