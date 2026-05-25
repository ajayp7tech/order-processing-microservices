package com.ajayp.orders.dto;
import com.ajayp.orders.domain.OrderStatus;
import java.math.BigDecimal; import java.time.LocalDateTime;
public record OrderResponse(String id, String customerId, BigDecimal totalAmount, String deliveryAddress, OrderStatus status, LocalDateTime createdAt) {}