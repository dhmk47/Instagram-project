package com.project.instagram.web.controller.api.auth;

import com.google.gson.Gson;
import com.project.instagram.handler.exception.CustomExceptionHandler;
import com.project.instagram.handler.exception.auth.AuthException;
import com.project.instagram.handler.exception.auth.AuthExceptionResult;
import com.project.instagram.service.auth.AuthService;
import com.project.instagram.web.controller.api.auth.AuthRestController;
import com.project.instagram.web.dto.user.CreateUserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthRestControllerTest {

    @InjectMocks
    private AuthRestController authRestController;

    @Mock
    private AuthService authService;

    private Gson gson;
    private MockMvc mockMvc;
    private CustomExceptionHandler customExceptionHandler;

    @BeforeEach
    public void init() {
        gson = new Gson();
        customExceptionHandler = new CustomExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(authRestController)
                .setControllerAdvice(customExceptionHandler)
                .build();
    }

    @Test
    void gson_mockMvc_isNotNull() {

        // then
        assertThat(gson).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void 회원가입실패_이미가입되어있는_아이디_ERROR_400() throws Exception {
        // given
        String url = "/api/v1/auth/sign-up";
        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .userId("dhmk47")
                .userPassword("123")
                .userName("한대경")
                .userNickname("testAccount")
                .build();

        when(authService.signUpUser(createUserRequestDto)).thenThrow(new AuthException(AuthExceptionResult.ALREADY_HAS_USER_EXCEPTION));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(createUserRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void 회원가입성공() throws Exception {
        // given
        String url = "/api/v1/auth/sign-up";
        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .userId("dhmk47@naver.com")
                .userPassword("123")
                .userName("한대경")
                .userNickname("testAccount")
                .build();

        when(authService.signUpUser(createUserRequestDto)).thenReturn(true);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(createUserRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void map객체_is_null_NPE_ERROR_500() throws Exception {
        // given
        Map<String, Object> oAuthResponseMap = new HashMap<>();

        String url = "/api/v1/auth/oauth/facebook";
        when(authService.loadFacebookLoginUser(oAuthResponseMap)).thenThrow(RuntimeException.class);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(oAuthResponseMap))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void map객체_제대로_전달됨() throws Exception{
        // given
        Map<String, Object> oAuthResponseMap = new HashMap<String, Object>();

        int userId = 123;
        String url = "/api/v1/auth/oauth/facebook";
        oAuthResponseMap.put("userId", userId);

        when(authService.loadFacebookLoginUser(oAuthResponseMap)).thenReturn(true);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                    .content(gson.toJson(oAuthResponseMap))
                    .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}