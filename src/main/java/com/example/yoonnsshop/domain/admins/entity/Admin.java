package com.example.yoonnsshop.domain.admins.entity;

import com.example.yoonnsshop.config.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(callSuper = true)
@Table(name = "admins")
public class Admin extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, name = "passwd")
    private String password;

    public Admin() {
    }

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Admin(Long seq, String email, String password) {
        super.seq = seq;
        this.email = email;
        this.password = password;
    }

    public Admin(Builder builder) {
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

        public Admin build() {
            return new Admin(this);
        }
    }
}
