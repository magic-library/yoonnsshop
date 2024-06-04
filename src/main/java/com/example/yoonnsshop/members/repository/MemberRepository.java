package com.example.yoonnsshop.members.repository;

import com.example.yoonnsshop.members.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    Member findByEmail(String email);
    Member save(Member user);
}
