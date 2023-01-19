package com.project.instagram.service.auth;

import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserRepository;
import com.project.instagram.handler.exception.auth.AuthException;
import com.project.instagram.web.dto.user.CreateUserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private AuthService authService;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        passwordEncoder = new BCryptPasswordEncoder();
        authService = new AuthServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void 이미가입되어있는아이디() {
        // given
        boolean status = false;

        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .userId("dhmk47")
                .userPassword("123")
                .userName("한대경")
                .userNickname("testAccount")
                .build();

        when(userRepository.findByUserId(createUserRequestDto.getUserId())).thenReturn(Optional.of(new User()));

        // when
        assertThatExceptionOfType(AuthException.class)
                .isThrownBy(() -> {
                    authService.signUpUser(createUserRequestDto);
                });

        // then

    }

    @Test
    void 회원가입성공() {
        // given
        boolean status = false;
        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .userId("dhmk47@naver.com")
                .userPassword("123")
                .userName("한대경")
                .userNickname("testAccount")
                .build();

        when(userRepository.findByUserId(createUserRequestDto.getUserId())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(any(User.class));

        // when
        status = authService.signUpUser(createUserRequestDto);

        // then
        assertThat(status).isTrue();
    }

    @Test
    void 로그인정보_불러오기_실패_NPE() {
        // given
        Map<String, Object> oAuthResponseMap = null;

        // when

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> {
                    authService.loadFacebookLoginUser(oAuthResponseMap);
                });

        // then

    }

    @Test
    void 로그인정보_불러오기_성공() {
        // given
        Map<String, Object> oAuthResponseMap = new HashMap<String, Object>();
        int userId = 123;
        oAuthResponseMap.put("user_id", userId);

        // when
        boolean result = authService.loadFacebookLoginUser(oAuthResponseMap);

        // then

        assertThat(result).isTrue();
    }
}