package com.pruebatecnica.orderservice.service;

import com.pruebatecnica.orderservice.dto.StockCheckResponse;
import com.pruebatecnica.orderservice.model.Order;
import com.pruebatecnica.orderservice.model.OrderItem;
import com.pruebatecnica.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        OrderItem item1 = new OrderItem(1L, 1L, 2, new BigDecimal("10.00"));
        OrderItem item2 = new OrderItem(2L, 2L, 1, new BigDecimal("20.00"));
        order = new Order(null, "Cliente Prueba", "test@test.com", null, null, null, List.of(item1, item2));
    }

    @DisplayName("Debería crear una orden exitosamente si hay stock")
    @Test
    void shouldCreateOrderWhenStockIsAvailable() {
        StockCheckResponse[] stockResponse = {
            new StockCheckResponse(1L, true),
            new StockCheckResponse(2L, true)
        };

        when(restTemplate.postForObject(
            any(String.class),
            any(List.class),
            eq(StockCheckResponse[].class))
        ).thenReturn(stockResponse);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals("CONFIRMED", createdOrder.getStatus());
        System.out.println("Prueba 'shouldCreateOrderWhenStockIsAvailable' pasada.");
    }

    @DisplayName("Debería lanzar una excepción si no hay stock")
    @Test
    void shouldThrowExceptionWhenStockIsNotAvailable() {
        StockCheckResponse[] stockResponse = {
            new StockCheckResponse(1L, true),
            new StockCheckResponse(2L, false)
        };

        when(restTemplate.postForObject(any(String.class), any(List.class), eq(StockCheckResponse[].class)))
            .thenReturn(stockResponse);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            orderService.createOrder(order);
        });

        assertEquals("Uno o más productos no tienen stock suficiente o no existen.", exception.getMessage());
        System.out.println("Prueba 'shouldThrowExceptionWhenStockIsNotAvailable' pasada.");
    }
}