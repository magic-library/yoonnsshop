package com.example.yoonnsshop.domain.orders;

import com.example.yoonnsshop.domain.items.entity.Item;
import com.example.yoonnsshop.domain.items.repository.ItemRepository;
import com.example.yoonnsshop.domain.members.entity.Member;
import com.example.yoonnsshop.domain.orders.dto.OrderDto;
import com.example.yoonnsshop.domain.orders.dto.OrderItemDto;
import com.example.yoonnsshop.domain.orders.dto.CreateOrderRequestDto;
import com.example.yoonnsshop.domain.orders.entity.Order;
import com.example.yoonnsshop.domain.orders.entity.OrderItem;
import com.example.yoonnsshop.domain.orders.entity.OrderStatus;
import com.example.yoonnsshop.domain.orders.repository.OrderItemRepository;
import com.example.yoonnsshop.domain.orders.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.itemRepository = itemRepository;
    }

    public List<Order> findAllByMemberId(Long memberId) {
        Pageable pageable = PageRequest.of(0, 10);
        // exper: JOIN
        return getOrdersForMember(memberId, pageable);
        // exper: origin
//        return orderRepository.findAllByMemberSeq(memberId, pageable);
    }

    @Transactional
    public Order generateOrderInvoice(CreateOrderRequestDto requestDto) {
        validateOrderRequestDto(requestDto);

        Order order = Order.Builder.anOrder()
                .withMember(getMember())
                .withStatus(OrderStatus.PAYMENT_COMPLETED)
                .build();

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemDto orderItemDto : requestDto.getOrderItems()) {
            Item item = itemRepository.findById(orderItemDto.getItemSeq())
                    .orElseThrow(() -> new IllegalArgumentException("Item not found"));

            OrderItem orderItem = OrderItem.Builder.anOrderItem()
                    .withOrder(order)
                    .withItem(item)
                    .withQuantity(orderItemDto.getQuantity())
                    .withPrice(item.getPrice())
                    .build();

            orderItemRepository.save(orderItem);

            totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(orderItemDto.getQuantity())));
        }

        order.calculateTotalPrice(totalPrice);
        orderRepository.save(order);

        return order;
    }

    private void validateOrderRequestDto(CreateOrderRequestDto order) {
        if (order.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("orderItems must be provided");
        }
    }

    // TODO: 추후 jwt로 대체
    private Member getMember() {
        return Member.Builder.aMember()
                .withSeq(1L)
                .build();
    }

    public List<OrderDto> getOwnerOrders() {
        Member member = getMember();
        Pageable pageable = PageRequest.of(0, 10);

        List<Order> orders = orderRepository.findAllByMemberSeq(member.getSeq(), pageable);
        List<OrderDto> orderDtos = orders.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());

        return orderDtos;
    }

    public List<OrderDto> getOwnerOrdersV2() {
        Member member = getMember();
        Pageable pageable = PageRequest.of(0, 10);

        List<Order> orders = getOrdersForMember(member.getSeq(), pageable);
        List<OrderDto> orderDtos = orders.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());

        return orderDtos;
    }

    public List<OrderDto> getOwnerOrdersV3() {
        Member member = getMember();
        Pageable pageable = PageRequest.of(0, 10);

        List<Order> orders = getOrdersForMember(member.getSeq(), pageable);
        List<OrderDto> orderDtos = orders.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());

        return orderDtos;
    }

    public List<OrderDto> getOwnerOrdersV4() {
        Member member = getMember();
        Pageable pageable = PageRequest.of(0, 1000);

        List<Order> orders = orderRepository.findAllByMemberSeq(member.getSeq(), pageable);
        List<OrderDto> orderDtos = orders.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());

        return orderDtos;
    }

    public List<Order> getOrdersForMember(Long memberId, Pageable pageable) {
        List<Long> orderIds = orderRepository.findOrderIdsByMemberSeq(memberId, pageable);
        return orderRepository.findOrdersWithItemsByIds(orderIds);
    }

    public List<Order> getOrdersForMemberUsingEntityGraph(Long memberId, Pageable pageable) {
        List<Long> orderIds = orderRepository.findOrderIdsByMemberSeq(memberId, pageable);
        return orderRepository.findOrdersWithItemsBySeqIn(orderIds);
    }


}