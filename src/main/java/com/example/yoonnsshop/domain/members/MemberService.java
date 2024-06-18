package com.example.yoonnsshop.domain.members;

import com.example.yoonnsshop.domain.members.dto.JoinDto;
import com.example.yoonnsshop.domain.members.entity.Member;
import com.example.yoonnsshop.domain.members.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

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
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> registerMember(JoinDto joinDto) {
        validateMemberDto(joinDto);
        Member member = Member.Builder.aMember()
                .withEmail(joinDto.getPrincipal())
                .build();

        try {
            return Optional.ofNullable(memberRepository.save(member));
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
