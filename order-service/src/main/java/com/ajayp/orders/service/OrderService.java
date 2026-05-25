package com.ajayp.orders.service;
import com.ajayp.orders.domain.*;
import com.ajayp.orders.dto.*;
import com.ajayp.orders.exception.OrderNotFoundException;
import com.ajayp.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @RequiredArgsConstructor @Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        Order o = new Order();
        o.setCustomerId(request.customerId());
        o.setTotalAmount(request.totalAmount());
        o.setDeliveryAddress(request.deliveryAddress());
        Order saved = orderRepository.save(o);
        log.info("Order placed: {}", saved.getId());
        return toResponse(saved);
    }
    public OrderResponse getOrder(String id) {
        return orderRepository.findById(id).map(this::toResponse).orElseThrow(() -> new OrderNotFoundException(id));
    }
    public List<OrderResponse> getOrdersByCustomer(String customerId) {
        return orderRepository.findByCustomerId(customerId).stream().map(this::toResponse).toList();
    }
    @Transactional
    public OrderResponse cancelOrder(String id) {
        Order o = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        o.setStatus(OrderStatus.CANCELLED);
        return toResponse(orderRepository.save(o));
    }
    private OrderResponse toResponse(Order o) {
        return new OrderResponse(o.getId(), o.getCustomerId(), o.getTotalAmount(), o.getDeliveryAddress(), o.getStatus(), o.getCreatedAt());
    }
}