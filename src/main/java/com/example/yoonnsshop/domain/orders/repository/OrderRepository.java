package com.example.yoonnsshop.domain.orders.repository;

import com.example.yoonnsshop.domain.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByMemberSeq(Long memberId);
}
