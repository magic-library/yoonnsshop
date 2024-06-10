package com.example.yoonnsshop.domain.orders.repository;

import com.example.yoonnsshop.domain.orders.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
}
