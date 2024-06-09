package com.example.yoonnsshop.domain.items.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemRequestDto {
    private String name;
    private BigDecimal price;
    private String description;
    private Integer stockQuantity;
}
