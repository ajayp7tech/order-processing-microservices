package com.ajayp.notification.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatNoException;
@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @InjectMocks private NotificationService notificationService;
    @Test void sendOrderPlacedNotification_shouldNotThrow() {
        assertThatNoException().isThrownBy(() ->
            notificationService.sendOrderPlacedNotification("ORD-001", "CUST-42"));
    }
    @Test void sendOrderConfirmedNotification_shouldNotThrow() {
        assertThatNoException().isThrownBy(() ->
            notificationService.sendOrderConfirmedNotification("ORD-001", "CUST-42"));
    }
}