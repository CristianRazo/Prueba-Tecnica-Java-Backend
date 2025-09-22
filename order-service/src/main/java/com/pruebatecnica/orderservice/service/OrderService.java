package com.pruebatecnica.orderservice.service;

import com.pruebatecnica.orderservice.dto.StockCheckResponse;
import com.pruebatecnica.orderservice.model.Order;
import com.pruebatecnica.orderservice.model.OrderItem;
import com.pruebatecnica.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.pruebatecnica.orderservice.dto.UpdateStockRequest;
import com.pruebatecnica.orderservice.dto.OrderItemDTO;
import java.util.Optional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final String PRODUCT_SERVICE_URL = "http://localhost:8081/api/products";

    /** Crea un pedido tras verificar stock y descontar inventario. */
    @Transactional
    public Order createOrder(Order order) {
        String checkStockUrl = PRODUCT_SERVICE_URL + "/check-availability";
        List<Long> productIds = order.getItems().stream()
                .map(OrderItem::getProductId)
                .toList();

        StockCheckResponse[] stockResponses = restTemplate.postForObject(
                checkStockUrl,
                productIds,
                StockCheckResponse[].class);

        if (stockResponses == null) {
            throw new IllegalStateException("No se pudo obtener la respuesta de stock del servicio de productos.");
        }

        boolean allProductsInStock = Arrays.stream(stockResponses).allMatch(StockCheckResponse::isInStock);

        if (!allProductsInStock) {
            throw new IllegalStateException("Uno o más productos no tienen stock suficiente o no existen.");
        }

        BigDecimal totalAmount = order.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);

        order.setOrderDate(LocalDateTime.now());
        order.setStatus("CONFIRMED");

        Order savedOrder = orderRepository.save(order);

        UpdateStockRequest stockRequest = new UpdateStockRequest();
        List<OrderItemDTO> itemsToUpdate = savedOrder.getItems().stream()
                .map(item -> {
                    OrderItemDTO dto = new OrderItemDTO();
                    dto.setProductId(item.getProductId());
                    dto.setQuantity(item.getQuantity());
                    return dto;
                })
                .toList();
        stockRequest.setItems(itemsToUpdate);

        restTemplate.postForObject(PRODUCT_SERVICE_URL + "/reduce-stock", stockRequest, Void.class);

        return savedOrder;
    }

    /** Devuelve todos los pedidos. */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /** Busca un pedido por ID. */
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}