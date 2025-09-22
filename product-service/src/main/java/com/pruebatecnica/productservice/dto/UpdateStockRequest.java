package com.pruebatecnica.productservice.dto;

import lombok.Data;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(name = "UpdateStockRequest", description = "Petición para reducir stock de productos")
public class UpdateStockRequest {
    @Schema(description = "Items a descontar del stock")
    private List<OrderItemDTO> items;
}

