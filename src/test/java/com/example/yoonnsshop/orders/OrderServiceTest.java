package com.example.yoonnsshop.orders;

import com.example.yoonnsshop.domain.orders.dto.OrderDto;
import com.example.yoonnsshop.domain.orders.entity.Order;
import com.example.yoonnsshop.domain.orders.repository.OrderRepository;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureTestEntityManager
@Transactional
public class OrderServiceTest {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void testLazyLoading() {
        Pageable pageable = PageRequest.of(0, 200);

        List<Order> orders = repository.findAllByMemberSeq(1L, pageable);
        String seqList = orders.stream()
                .map(Order::getSeq)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        System.out.println("Order Seq List Size: {}" + seqList);


        List<OrderDto> orderDtos = orders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        int totalOrderItems = 0;
        for (OrderDto orderDto : orderDtos) {
            totalOrderItems += orderDto.getOrderItems().size();
        }

        System.out.println("Total number of Order: " + orderDtos.size());
        System.out.println("Total number of OrderItems: " + totalOrderItems);
    }
}
