package com.example.yoonnsshop.members;

import com.example.yoonnsshop.members.domain.Member;
import com.example.yoonnsshop.members.dto.JoinDto;
import com.example.yoonnsshop.members.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        List<Member> members = memberRepository.findAll();
        return members;
    }

    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(memberRepository.findByEmail(email));
    }

    public Optional<Member> registerMember(JoinDto joinDto) {
        validateMemberDto(joinDto);
        Member member = new Member.Builder()
                .withEmail(joinDto.getPrincipal())
                .withPassword(joinDto.getCredentials())
                .build();

        try {
            Member savedMember = memberRepository.save(member);
            return Optional.of(savedMember);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private void validateMemberDto(JoinDto joinDto) {
        if (joinDto.getPrincipal().isEmpty() || joinDto.getCredentials().isEmpty()) {
            throw new IllegalArgumentException("Email and password must be provided");
        }
    }
}
