package com.ajayp.orders.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record OrderRequest(@NotBlank String customerId, @NotNull @Positive BigDecimal totalAmount, @NotBlank String deliveryAddress) {}