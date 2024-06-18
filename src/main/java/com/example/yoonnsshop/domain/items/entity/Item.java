package com.example.yoonnsshop.domain.items.entity;

import com.example.yoonnsshop.common.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString(callSuper = true)
@Table(name = "items")
@AttributeOverride(name = "seq", column = @Column(name = "item_id"))
@RequiredArgsConstructor
@FilterDef(name = "activeFilter", defaultCondition = "active = true")
@Filter(name = "activeFilter")
public class Item extends BaseEntity {
    @Column(name = "item_name")
    private String name;

    @Column(name = "item_description")
    private String description;

    @Column(name = "item_price")
    private BigDecimal price;

    @Column(name = "item_stock_quantity")
    private Integer stockQuantity;

    @Column(nullable = false)
    private boolean active = true; // 기본값은 true(활성화 상태)

    public static final class Builder {
        private String name;
        private String description = "";
        private BigDecimal price = BigDecimal.ZERO;
        private Integer stockQuantity = 0;
        private Long seq;
        private LocalDateTime createAt;
        private LocalDateTime updatedAt;

        private Builder() {
        }

        public static Builder anItem() {
            return new Builder();
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withStockQuantity(Integer stockQuantity) {
            this.stockQuantity = stockQuantity;
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

        public Item build() {
            Item item = new Item();
            item.name = this.name;
            item.description = this.description;
            item.price = this.price;
            item.stockQuantity = this.stockQuantity;
            item.seq = this.seq;
            item.createAt = this.createAt;
            item.updatedAt = this.updatedAt;
            return item;
        }
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
