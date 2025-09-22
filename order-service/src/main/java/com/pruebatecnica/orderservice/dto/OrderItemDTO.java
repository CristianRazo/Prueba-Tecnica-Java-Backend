package com.pruebatecnica.orderservice.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(name = "OrderItemDTO", description = "Item de pedido para actualización de stock")
public class OrderItemDTO {
    @Schema(description = "ID del producto", example = "1")
    private Long productId;
    @Schema(description = "Cantidad solicitada", example = "3")
    private Integer quantity;
}