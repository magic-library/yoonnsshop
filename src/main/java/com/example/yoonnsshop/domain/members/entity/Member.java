package com.example.yoonnsshop.domain.members.entity;

import com.example.yoonnsshop.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(callSuper = true)
@Table(name = "members")
@RequiredArgsConstructor
@AttributeOverride(name = "seq", column = @Column(name = "member_id"))
public class Member extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String name;

    public static final class Builder {
        private String email;
        private String name;
        private Long seq;
        private LocalDateTime createAt;
        private LocalDateTime updatedAt;

        private Builder() {
        }

        public static Builder aMember() {
            return new Builder();
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
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

        public Member build() {
            Member member = new Member();
            member.email = this.email;
            member.name = this.name;
            member.seq = this.seq;
            member.createAt = this.createAt;
            member.updatedAt = this.updatedAt;

            return member;
        }
    }
}
