package com.example.yoonnsshop.domain.orders.entity;

import com.example.yoonnsshop.common.BaseEntity;
import com.example.yoonnsshop.domain.members.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString(callSuper = true)
@Table(name = "orders")
@AttributeOverride(name = "seq", column = @Column(name = "order_id"))
public class Order extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus status;

    @Column(name = "order_total_price")
    private BigDecimal totalPrice;

    public static final class Builder {
        private Member member;
        private OrderStatus status;
        private BigDecimal totalPrice;
        private Long seq;
        private LocalDateTime createAt;
        private LocalDateTime updatedAt;

        private Builder() {
        }

        public static Builder anOrder() {
            return new Builder();
        }

        public Builder withMember(Member member) {
            this.member = member;
            return this;
        }

        public Builder withStatus(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder withTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder withSeq(Long seq) {
            this.seq = seq;
            return this;
        }

        public Builder withCreateAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }

        public Builder withUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.status = this.status;
            order.updatedAt = this.updatedAt;
            order.member = this.member;
            order.seq = this.seq;
            order.totalPrice = this.totalPrice;
            order.createAt = this.createAt;
            return order;
        }
    }

    public void calculateTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
