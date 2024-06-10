package com.example.yoonnsshop.domain.orders.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateOrderRequestDto {
    @NotNull
    private List<OrderItemDto> orderItems;
}

