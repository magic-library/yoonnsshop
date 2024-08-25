package com.example.yoonnsshop.orders;

import com.example.yoonnsshop.domain.items.entity.Item;
import com.example.yoonnsshop.domain.items.repository.ItemRepository;
import com.example.yoonnsshop.domain.orders.OrderService;
import com.example.yoonnsshop.domain.orders.dto.CreateOrderRequestDto;
import com.example.yoonnsshop.domain.orders.dto.OrderItemDto;
import com.example.yoonnsshop.domain.orders.entity.Order;
import com.example.yoonnsshop.domain.orders.entity.OrderStatus;
import com.example.yoonnsshop.domain.orders.repository.OrderItemRepository;
import com.example.yoonnsshop.domain.orders.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    OrderService orderService;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    OrderItemRepository orderItemRepository;

    @MockBean
    ItemRepository itemRepository;

    private final String baseUrl = "/api/v1/orders";

    @Test
    @DisplayName("주문 생성")
    public void createOrder() throws Exception{
        // given
        OrderItemDto orderItemDto = new OrderItemDto(); // 기본 생성자 사용
        orderItemDto.setItemSeq(1L); // itemId 대신 itemSeq 사용
        orderItemDto.setName("Test Item"); // 이름 설정
        orderItemDto.setQuantity(2); // 수량 설정

        List<OrderItemDto> orderItems = new ArrayList<>();
        orderItems.add(orderItemDto);

        CreateOrderRequestDto requestDto = new CreateOrderRequestDto();
        requestDto.setOrderItems(orderItems);

        Order order = Order.Builder.anOrder()
                .withStatus(OrderStatus.PAYMENT_COMPLETED) // 기본 상태를 설정합니다.
                .build();

        Item item = Item.Builder.anItem()
                .withName("Default Name") // 기본 이름을 설정합니다.
                .withDescription("Default Description") // 기본 설명을 설정합니다.
                .withPrice(BigDecimal.ZERO) // 기본 가격을 설정합니다.
                .withStockQuantity(0) // 기본 재고 수량을 설정합니다.
                .build();

        given(itemRepository.findById(1L)).willReturn(Optional.of(item));
        given(orderRepository.save(any(Order.class))).willReturn(order);

        // when
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    @DisplayName("자신의 주문 조회")
    public void getOwnerOrders() throws Exception {
        // given
        Order order = Order.Builder.anOrder()
                .withStatus(OrderStatus.PAYMENT_COMPLETED)
                .build();

        List<Order> orders = new ArrayList<>();
        orders.add(order);

//        given(orderRepository.findAllByMemberSeq(1L, pageable)).willReturn(orders);

        // when
        mockMvc.perform(get(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true));
    }
}
