package com.ajayp.orders.domain;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Table(name = "orders") @Data
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private String id;
    @Column(nullable = false) private String customerId;
    @Column(nullable = false) private BigDecimal totalAmount;
    private String deliveryAddress;
    @Enumerated(EnumType.STRING) private OrderStatus status = OrderStatus.PLACED;
    private LocalDateTime createdAt = LocalDateTime.now();
}