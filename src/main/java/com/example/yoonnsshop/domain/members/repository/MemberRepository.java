package com.example.yoonnsshop.domain.members.repository;

import com.example.yoonnsshop.domain.members.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    Member findByEmail(String email);
    Member save(Member user);
}
