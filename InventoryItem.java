package com.ajayp.inventory.domain;
import jakarta.persistence.*;
import lombok.Data;
@Entity @Table(name = "inventory") @Data
public class InventoryItem {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private String id;
    @Column(nullable = false, unique = true) private String productId;
    @Column(nullable = false) private Integer quantity;
}