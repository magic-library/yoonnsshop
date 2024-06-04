package com.example.yoonnsshop.members;

import com.example.yoonnsshop.members.domain.Member;
import com.example.yoonnsshop.members.dto.JoinDto;
import com.example.yoonnsshop.members.dto.ResponseMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/members")
public class MemberController {
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("{memberSeq}")
    public ResponseEntity<Member> getMemberById(@PathVariable("memberSeq")Long memberId) {
        Optional<Member> optionalMember = memberService.findById(memberId);
        return optionalMember.map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.findAll();
        return ResponseEntity.ok().body(members);
    }

    @PostMapping("join")
    public ResponseEntity<ResponseMessageDto> join(@RequestBody JoinDto joinDto) {
        if (memberService.findByEmail(joinDto.getPrincipal()).isPresent()) {
            ResponseMessageDto responseMessageDto = new ResponseMessageDto(false, "Email already exists");
            return ResponseEntity.badRequest().body(responseMessageDto);
        }

        Optional<Member> member = memberService.registerMember(joinDto);
        if (member.isEmpty()) {
            ResponseMessageDto responseMessageDto = new ResponseMessageDto(false, "Failed to join");
            return ResponseEntity.badRequest().body(responseMessageDto);
        }

        ResponseMessageDto responseMessageDto = new ResponseMessageDto(true, "Member joined successfully");
        return ResponseEntity.ok(responseMessageDto);
    }
}
