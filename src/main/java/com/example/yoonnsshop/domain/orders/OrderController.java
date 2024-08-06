package com.example.yoonnsshop.domain.orders;

import com.example.yoonnsshop.common.ApiResponse;
import com.example.yoonnsshop.config.ApiController;
import com.example.yoonnsshop.domain.orders.dto.CreateOrderRequestDto;
import com.example.yoonnsshop.domain.orders.dto.OrderDto;
import com.example.yoonnsshop.domain.orders.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@ApiController
@RequestMapping("orders")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getOwnerOrders() {
        List<OrderDto> orders = orderService.getOwnerOrders();
        return ResponseEntity.ok(new ApiResponse(true, orders));
    }

    @GetMapping("v2") // Join
    public ResponseEntity<ApiResponse> getOwnerOrdersV2() {
        List<OrderDto> orders = orderService.getOwnerOrdersV2();
        return ResponseEntity.ok(new ApiResponse(true, orders));
    }

    @GetMapping("v3") // EntityGraph
    public ResponseEntity<ApiResponse> getOwnerOrdersV3() {
        List<OrderDto> orders = orderService.getOwnerOrdersV3();
        return ResponseEntity.ok(new ApiResponse(true, orders));
    }

    @GetMapping("v4") // Batch
    public ResponseEntity<ApiResponse> getOwnerOrdersV4() {
        List<OrderDto> orders = orderService.getOwnerOrdersV4();
        return ResponseEntity.ok(new ApiResponse(true, orders));
    }

    @GetMapping("v5") // BatchSize
    public ResponseEntity<ApiResponse> getOwnerOrdersV5() {
        List<OrderDto> orders = orderService.getOwnerOrdersV5();
        return ResponseEntity.ok(new ApiResponse(true, orders));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        Order order = orderService.generateOrderInvoice(requestDto);
        return ResponseEntity.ok(new ApiResponse(true, order));
    }
}
