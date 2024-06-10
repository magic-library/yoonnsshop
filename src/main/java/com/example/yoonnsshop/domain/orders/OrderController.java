package com.example.yoonnsshop.domain.orders;

import com.example.yoonnsshop.common.ApiResponse;
import com.example.yoonnsshop.config.ApiController;
import com.example.yoonnsshop.domain.orders.dto.CreateOrderRequestDto;
import com.example.yoonnsshop.domain.orders.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@ApiController
@RequestMapping("orders")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getOwnerOrders() {
        orderService.getOwnerOrders();
        return ResponseEntity.ok(new ApiResponse(true, orderService.findAllByMemberId(1L)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        Order order = orderService.generateOrderInvoice(requestDto);
        return ResponseEntity.ok(new ApiResponse(true, order));
    }
}
