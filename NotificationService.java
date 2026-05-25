package com.ajayp.notification.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Service @Slf4j
public class NotificationService {
    public void sendOrderPlacedNotification(String orderId, String customerId) {
        log.info("Notification: Order {} placed for customer {}", orderId, customerId);
    }
    public void sendOrderConfirmedNotification(String orderId, String customerId) {
        log.info("Notification: Order {} confirmed for customer {}", orderId, customerId);
    }
    public void sendOrderCancelledNotification(String orderId, String customerId) {
        log.info("Notification: Order {} cancelled for customer {}", orderId, customerId);
    }
}