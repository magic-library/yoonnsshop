package com.example.yoonnsshop.members;

import com.example.yoonnsshop.domain.members.entity.Member;
import com.example.yoonnsshop.domain.members.repository.MemberRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MemberServiceTest {
    @Autowired
    private MemberRepository userRepository;

    @Test
    @Description("회원 등록 테스트")
    public void registerMember() {
        // given
        Member user = new Member("test@example.com", "password");

        // when
        Member savedMember = userRepository.save(user);


        // then
        assertNotNull(savedMember);
        assertEquals(user.getEmail(), savedMember.getEmail());
        assertEquals(user.getPassword(), savedMember.getPassword());
    }
}
