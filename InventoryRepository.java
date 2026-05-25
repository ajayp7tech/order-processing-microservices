package com.ajayp.inventory.repository;
import com.ajayp.inventory.domain.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface InventoryRepository extends JpaRepository<InventoryItem, String> {
    Optional<InventoryItem> findByProductId(String productId);
}