package com.pruebatecnica.orderservice.dto; 

import lombok.Data;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(name = "UpdateStockRequest", description = "Petición para descontar stock en product-service")
public class UpdateStockRequest {
    @Schema(description = "Lista de items a descontar")
    private List<OrderItemDTO> items;
}