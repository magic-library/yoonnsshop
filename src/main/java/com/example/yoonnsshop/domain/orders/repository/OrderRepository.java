package com.example.yoonnsshop.domain.orders.repository;

import com.example.yoonnsshop.domain.orders.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.hibernate.annotations.QueryHints;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByMemberSeq(Long memberId, Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH oi.item WHERE o.seq IN :orderIds")
    List<Order> findOrdersWithItemsByIds(@Param("orderIds") List<Long> orderIds);

    // exper: JOIN
    @Query("SELECT o.seq FROM Order o WHERE o.member.seq = :memberId")
    List<Long> findOrderIdsByMemberSeq(@Param("memberId") Long memberId, Pageable pageable);

    // exper: EntityGraph
    @EntityGraph(attributePaths = {"orderItems", "orderItems.item"})
    List<Order> findOrdersWithItemsBySeqIn(List<Long> orderIds);
}
