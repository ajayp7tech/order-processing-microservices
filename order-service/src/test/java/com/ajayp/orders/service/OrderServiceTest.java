package com.ajayp.orders.service;
import com.ajayp.orders.domain.Order;
import com.ajayp.orders.domain.OrderStatus;
import com.ajayp.orders.dto.*;
import com.ajayp.orders.exception.OrderNotFoundException;
import com.ajayp.orders.repository.OrderRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock private OrderRepository orderRepository;
    @InjectMocks private OrderService orderService;
    private Order testOrder;
    @BeforeEach void setUp() {
        testOrder = new Order();
        testOrder.setId("ORD-001");
        testOrder.setCustomerId("CUST-42");
        testOrder.setTotalAmount(new BigDecimal("59.99"));
        testOrder.setDeliveryAddress("123 Main St");
        testOrder.setStatus(OrderStatus.PLACED);
    }
    @Test void placeOrder_shouldReturnOrderResponse() {
        when(orderRepository.save(any())).thenReturn(testOrder);
        OrderResponse resp = orderService.placeOrder(new OrderRequest("CUST-42", new BigDecimal("59.99"), "123 Main St"));
        assertThat(resp.customerId()).isEqualTo("CUST-42");
        assertThat(resp.status()).isEqualTo(OrderStatus.PLACED);
    }
    @Test void getOrder_shouldThrowWhenNotFound() {
        when(orderRepository.findById("BAD")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderService.getOrder("BAD")).isInstanceOf(OrderNotFoundException.class);
    }
    @Test void cancelOrder_shouldSetStatusCancelled() {
        when(orderRepository.findById("ORD-001")).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any())).thenReturn(testOrder);
        orderService.cancelOrder("ORD-001");
        verify(orderRepository).save(argThat(o -> o.getStatus() == OrderStatus.CANCELLED));
    }
}
