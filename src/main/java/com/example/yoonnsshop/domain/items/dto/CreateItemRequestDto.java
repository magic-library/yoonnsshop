package com.example.yoonnsshop.domain.items.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateItemRequestDto {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String description;
    @NotNull
    private Integer stockQuantity;
}
