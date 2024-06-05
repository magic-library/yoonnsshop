package com.example.yoonnsshop.members;


import com.example.yoonnsshop.domain.members.MemberService;
import com.example.yoonnsshop.domain.members.entity.Member;
import com.example.yoonnsshop.domain.members.dto.JoinDto;
import com.example.yoonnsshop.domain.members.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @MockBean
    MemberRepository memberRepository;

    private final String baseUrl = "/api/members";

    @Test
    @DisplayName("회원 리스트 조회")
    public void getAllMembers() throws Exception {
        // given
        Member member1 = new Member(1L, "email1@test.com", "password1");
        Member member2 = new Member(2L ,"email2@test.com", "password2");
        Member member3 = new Member(3L, "email3@test.com", "password3");

        // when
        when(memberRepository.findAll()).thenReturn(Arrays.asList(member1, member2, member3));

        // then
        this.mockMvc.perform(get(baseUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))) // 응답 본문에 3명의 사용자가 있는지 확인합니다.
                .andExpect(jsonPath("$[0].email", is(member1.getEmail()))) // 첫 번째 사용자의 이메일이 맞는지 확인합니다.
                .andExpect(jsonPath("$[1].email", is(member2.getEmail()))) // 두 번째 사용자의 이메일이 맞는지 확인합니다.
                .andExpect(jsonPath("$[2].email", is(member3.getEmail()))); // 세 번째 사용자의 이메일이 맞는지 확인합니다.
    }

    @Test
    @DisplayName("회원 시퀀스로 조회")
    public void getMemberById() throws Exception {
        // given
        Member member = new Member(1L, "email1@Test.com", "password1");

        // when
        when(memberRepository.findById(1L)).thenReturn(java.util.Optional.of(member));

        this.mockMvc.perform(get(baseUrl + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(member.getEmail())));
    }

    @Test
    @DisplayName("회원가입")
    public void join() throws Exception {
        // given
        JoinDto joinDto = new JoinDto("email1@test.com", "password1");
        ObjectMapper objectMapper = new ObjectMapper();
        String joinDtoJson = objectMapper.writeValueAsString(joinDto);

        // when
        this.mockMvc.perform(post(baseUrl + "/join")
                        .content(joinDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk());
    }
}
