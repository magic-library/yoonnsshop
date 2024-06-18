package com.example.yoonnsshop.domain.members;

import com.example.yoonnsshop.config.ApiController;
import com.example.yoonnsshop.domain.members.dto.JoinDto;
import com.example.yoonnsshop.domain.members.entity.Member;
import com.example.yoonnsshop.common.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@ApiController
@RequestMapping("members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // TODO: 반환형 통일
    @GetMapping("{memberId}")
    public ResponseEntity<Member> getMemberById(@PathVariable("memberId")Long memberId) {
        Optional<Member> optionalMember = memberService.findById(memberId);
        return optionalMember.map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.findAll();
        return ResponseEntity.ok().body(members);
    }

    // TODO: service / controller 역할 분리
    @PostMapping("join")
    public ResponseEntity<ApiResponse> join(@RequestBody @Valid JoinDto joinDto) {
        if (memberService.findByEmail(joinDto.getPrincipal()).isPresent()) {
            ApiResponse apiResponse = new ApiResponse(false, "Email already exists");
            return ResponseEntity.badRequest().body(apiResponse);
        }

        Optional<Member> member = memberService.registerMember(joinDto);
        if (member.isEmpty()) {
            ApiResponse apiResponse = new ApiResponse(false, "Failed to join");
            return ResponseEntity.badRequest().body(apiResponse);
        }

        ApiResponse apiResponse = new ApiResponse(true, "Member joined successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
