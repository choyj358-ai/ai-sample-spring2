package com.example.demo.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("회원가입 성공 테스트 - Form 방식")
    @Test
    public void join_success_test() throws Exception {
        // given
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .param("username", "ssar1234")
                        .param("password", "12341234")
                        .param("email", "ssar@nate.com")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        );

        // then
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/login-form"));
    }

    @DisplayName("회원가입 실패 테스트 - 유효성 검사 미통과 (username 짧음) - Form 방식")
    @Test
    public void join_fail_test_username_short() throws Exception {
        // given
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .param("username", "ss") // 4자 미만
                        .param("password", "12341234")
                        .param("email", "ssar@nate.com")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(content().string(org.hamcrest.Matchers.containsString("유저네임은 4~20자 이내여야 합니다")));
    }

    @DisplayName("회원가입 실패 테스트 - 유효성 검사 미통과 (email 형식 오류) - Form 방식")
    @Test
    public void join_fail_test_email_format() throws Exception {
        // given
        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .param("username", "ssar1234")
                        .param("password", "12341234")
                        .param("email", "ssar-nate.com") // @ 누락
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(content().string(org.hamcrest.Matchers.containsString("이메일 형식이 올바르지 않습니다")));
    }
}
