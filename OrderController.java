package com.ajayp.orders.controller;
import com.ajayp.orders.dto.*;
import com.ajayp.orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/orders") @RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(req));
    }
    @GetMapping("/{id}") public ResponseEntity<OrderResponse> getOrder(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }
    @GetMapping("/customer/{customerId}") public ResponseEntity<List<OrderResponse>> getByCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }
    @PutMapping("/{id}/cancel") public ResponseEntity<OrderResponse> cancel(@PathVariable String id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}