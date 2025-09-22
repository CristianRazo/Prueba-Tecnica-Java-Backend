package com.pruebatecnica.productservice.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(name = "OrderItemDTO", description = "Item usado para verificar/reducir stock")
public class OrderItemDTO {
    @Schema(description = "ID del producto", example = "1")
    private Long productId;
    @Schema(description = "Cantidad a verificar o descontar", example = "5")
    private Integer quantity;
}