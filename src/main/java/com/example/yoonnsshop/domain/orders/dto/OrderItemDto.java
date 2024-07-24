package com.example.yoonnsshop.domain.orders.dto;

import com.example.yoonnsshop.domain.orders.entity.OrderItem;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class OrderItemDto {
    @NotNull
    Long itemSeq;
    @NotNull
    String name;
    @NotNull
    Integer quantity;

    public OrderItemDto(OrderItem orderItem) {
        this.itemSeq = orderItem.getItemSeq();
        this.name = orderItem.getItemName();
        this.quantity = orderItem.getQuantity();
    }
}
