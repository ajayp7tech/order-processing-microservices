package com.ajayp.inventory.service;
import com.ajayp.inventory.domain.InventoryItem;
import com.ajayp.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @RequiredArgsConstructor @Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    public boolean isInStock(String productId, int quantity) {
        return inventoryRepository.findByProductId(productId)
            .map(i -> i.getQuantity() >= quantity).orElse(false);
    }
    @Transactional
    public void reserveStock(String productId, int quantity) {
        InventoryItem item = inventoryRepository.findByProductId(productId)
            .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
        if (item.getQuantity() < quantity) throw new RuntimeException("Insufficient stock for: " + productId);
        item.setQuantity(item.getQuantity() - quantity);
        inventoryRepository.save(item);
        log.info("Reserved {} units of {}", quantity, productId);
    }
}