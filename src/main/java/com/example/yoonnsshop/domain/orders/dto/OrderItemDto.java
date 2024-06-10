package com.example.yoonnsshop.domain.orders.dto;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderItemDto {
    @NotNull
    Long itemId;
    @NotNull
    Integer quantity;
}
