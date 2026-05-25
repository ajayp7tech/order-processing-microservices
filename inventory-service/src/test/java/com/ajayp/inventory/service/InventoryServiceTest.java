package com.ajayp.inventory.service;
import com.ajayp.inventory.domain.InventoryItem;
import com.ajayp.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {
    @Mock private InventoryRepository inventoryRepository;
    @InjectMocks private InventoryService inventoryService;
    @Test void isInStock_shouldReturnTrueWhenSufficient() {
        InventoryItem item = new InventoryItem();
        item.setProductId("PRD-001");
        item.setQuantity(10);
        when(inventoryRepository.findByProductId("PRD-001")).thenReturn(Optional.of(item));
        assertThat(inventoryService.isInStock("PRD-001", 5)).isTrue();
    }
    @Test void isInStock_shouldReturnFalseWhenInsufficient() {
        InventoryItem item = new InventoryItem();
        item.setProductId("PRD-001");
        item.setQuantity(2);
        when(inventoryRepository.findByProductId("PRD-001")).thenReturn(Optional.of(item));
        assertThat(inventoryService.isInStock("PRD-001", 5)).isFalse();
    }
}