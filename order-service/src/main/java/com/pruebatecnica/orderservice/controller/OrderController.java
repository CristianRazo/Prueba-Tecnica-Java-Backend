package com.pruebatecnica.orderservice.controller;

import com.pruebatecnica.orderservice.model.Order;
import com.pruebatecnica.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Operaciones sobre pedidos")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Crea un nuevo pedido")
    @ApiResponse(responseCode = "201", description = "Pedido creado",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Order.class),
            examples = @ExampleObject(name = "Pedido creado",
                value = "{\n  \"id\": 1001,\n  \"customerName\": \"Juan Pérez\",\n  \"customerEmail\": \"juan.perez@example.com\",\n  \"orderDate\": \"2025-09-22T13:34:33.926Z\",\n  \"status\": \"CONFIRMED\",\n  \"totalAmount\": 199.99,\n  \"items\": [ { \n    \"id\": 10, \n    \"productId\": 1, \n    \"quantity\": 2, \n    \"unitPrice\": 99.99 \n  } ]\n}")))
    @ApiResponse(responseCode = "400", description = "Solicitud inválida o stock insuficiente",
        content = @Content(mediaType = "application/json",
            examples = @ExampleObject(name = "Error",
                value = "{\n  \"status\": 400,\n  \"error\": \"Bad Request\",\n  \"message\": \"Uno o más productos no tienen stock suficiente o no existen.\"\n}")))
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order newOrder = orderService.createOrder(order);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Obtiene todos los pedidos")
    @ApiResponse(responseCode = "200", description = "Listado de pedidos",
        content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Order.class))))
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Busca un pedido por ID")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Order.class),
            examples = @ExampleObject(name = "Pedido encontrado",
                value = "{\n  \"id\": 1001,\n  \"customerName\": \"Juan Pérez\",\n  \"customerEmail\": \"juan.perez@example.com\",\n  \"orderDate\": \"2025-09-22T13:34:33.926Z\",\n  \"status\": \"CONFIRMED\",\n  \"totalAmount\": 199.99,\n  \"items\": [ { \n    \"id\": 10, \n    \"productId\": 1, \n    \"quantity\": 2, \n    \"unitPrice\": 99.99 \n  } ]\n}")))
    @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
        content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}