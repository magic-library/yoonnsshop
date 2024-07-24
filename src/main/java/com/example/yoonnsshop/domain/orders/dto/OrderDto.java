package com.example.yoonnsshop.domain.orders.dto;

import com.example.yoonnsshop.domain.orders.entity.Order;
import com.example.yoonnsshop.domain.orders.entity.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public class OrderDto {
    private Long seq;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        this.seq = order.getSeq();
        this.createAt = order.getCreateAt();
        this.updatedAt = order.getUpdatedAt();
        this.status = order.getStatus();
        this.totalPrice = order.getTotalPrice();
        this.orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(Collectors.toList());
    }
}
