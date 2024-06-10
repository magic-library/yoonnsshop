package com.example.yoonnsshop.domain.admins.entity;

import com.example.yoonnsshop.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@ToString(callSuper = true)
@Table(name = "admins")
@AttributeOverride(name = "seq", column = @Column(name = "admin_id"))
@RequiredArgsConstructor
public class Admin extends BaseEntity implements UserDetails {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, name = "passwd")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }


    public static final class Builder {
        private String email;
        private String password;
        private Long seq;
        private LocalDateTime createAt;
        private LocalDateTime updatedAt;

        private Builder() {
        }

        public static Builder anAdmin() {
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

        public Admin build() {
            Admin admin = new Admin();
            admin.updatedAt = this.updatedAt;
            admin.password = this.password;
            admin.createAt = this.createAt;
            admin.email = this.email;
            admin.seq = this.seq;
            return admin;
        }
    }
}
