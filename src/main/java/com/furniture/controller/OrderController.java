package com.furniture.controller;

import com.furniture.dto.OrderRequest;
import com.furniture.dto.OrderResponse;
import com.furniture.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestParam String username,
            @Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.placeOrder(username, request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getUserOrders(
            @RequestParam String username) {
        return ResponseEntity.ok(orderService.getUserOrders(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(
            @RequestParam String username,
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id, username));
    }
}
