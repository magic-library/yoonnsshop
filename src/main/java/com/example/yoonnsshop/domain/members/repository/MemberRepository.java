package com.example.yoonnsshop.domain.members.repository;

import com.example.yoonnsshop.domain.members.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByEmail(String email);
    Member save(Member user);
}
