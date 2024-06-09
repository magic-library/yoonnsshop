package com.example.yoonnsshop.domain.members.entity;

import com.example.yoonnsshop.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString(callSuper = true)
@Table(name = "members")
@AttributeOverride(name = "seq", column = @Column(name = "member_id"))
public class Member extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, name = "passwd")
    private String password;

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
