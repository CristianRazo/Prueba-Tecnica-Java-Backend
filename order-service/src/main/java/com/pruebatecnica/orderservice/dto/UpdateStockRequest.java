package com.pruebatecnica.orderservice.dto; 

import lombok.Data;
import java.util.List;

@Data
public class UpdateStockRequest {
    private List<OrderItemDTO> items;
}