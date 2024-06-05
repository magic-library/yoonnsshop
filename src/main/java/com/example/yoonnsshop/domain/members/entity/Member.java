package com.example.yoonnsshop.domain.members.entity;

import com.example.yoonnsshop.config.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(callSuper = true)
@Table(name = "members")
public class Member extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, name = "passwd")
    private String password;

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime lastLoginAt;

    public Member() {
    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member(Long seq, String email, String password) {
        super.seq = seq;
        this.email = email;
        this.password = password;
    }

    public Member(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
    }

    public static final class Builder {
        private String email;
        private String password;

        public static Builder anUser() {
            return new Builder();
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }
}
