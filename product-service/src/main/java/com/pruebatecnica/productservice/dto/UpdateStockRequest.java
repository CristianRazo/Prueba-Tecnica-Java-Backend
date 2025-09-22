package com.pruebatecnica.productservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class UpdateStockRequest {
    private List<OrderItemDTO> items;
}

