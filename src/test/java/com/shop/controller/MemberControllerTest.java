package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc //MockMVC 테스트를 위함.
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc; //실제 객체와 비슷한 테스트용 객체 생성. 웹브라우저에서 요청하는 것 처럼 테스트 가능.
    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(String email, String password) {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword(password);
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccessTest() throws Exception {
        // given
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);

        // then
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/members/login")
                        .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated()); //로그인 성공시 테스트 통과
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void loginFailTest() throws Exception{
        // given
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);

        // then
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/members/login")
                        .user(email).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated()); //로그인 실패시 테스트 통과
    }
}