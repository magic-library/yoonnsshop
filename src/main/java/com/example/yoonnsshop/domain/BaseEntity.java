package com.example.yoonnsshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long seq;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime createAt;

//    @LastModifiedBy
//    @Temporal(TemporalType.TIMESTAMP)
//    protected LocalDateTime updatedAt;
}
