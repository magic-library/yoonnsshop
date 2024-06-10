package com.example.yoonnsshop.domain.orders.entity;

import com.example.yoonnsshop.common.BaseEntity;
import com.example.yoonnsshop.domain.items.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString(callSuper = true)
@Table(name = "order_items")
@AttributeOverride(name = "seq", column = @Column(name = "order_item_id"))
public class OrderItem extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "item_price")
    private BigDecimal price;

    @Column(name = "item_quantity")
    private Integer quantity;

    public static final class Builder {
        private Item item;
        private Order order;
        private BigDecimal price;
        private Integer quantity;
        private Long seq;
        private LocalDateTime createAt;
        private LocalDateTime updatedAt;

        private Builder() {
        }

        public static Builder anOrderItem() {
            return new Builder();
        }

        public Builder withItem(Item item) {
            this.item = item;
            return this;
        }

        public Builder withOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withQuantity(Integer quantity) {
            this.quantity = quantity;
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

        public OrderItem build() {
            OrderItem orderItem = new OrderItem();
            orderItem.quantity = this.quantity;
            orderItem.item = this.item;
            orderItem.price = this.price;
            orderItem.updatedAt = this.updatedAt;
            orderItem.seq = this.seq;
            orderItem.createAt = this.createAt;
            orderItem.order = this.order;
            return orderItem;
        }
    }
}
