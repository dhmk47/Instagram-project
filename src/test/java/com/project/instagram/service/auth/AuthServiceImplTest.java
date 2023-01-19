package com.project.instagram.service.auth;

import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserDetailRepository;
import com.project.instagram.domain.user.UserRepository;
import com.project.instagram.handler.exception.auth.AuthException;
import com.project.instagram.web.dto.user.CreateUserRequestDto;
import com.project.instagram.web.dto.user.ReadUserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    @Mock
    private UserDetailRepository userDetailRepository;
    private AuthService authService;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        passwordEncoder = new BCryptPasswordEncoder();
        userDetailsService = new PrincipalDetailsService(userRepository);
        authService = new AuthServiceImpl(userRepository, userDetailRepository, passwordEncoder);
    }

    @Test
    void 이미가입되어있는아이디_Exception() {
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
    void 로그인실페_아이디_없음() {
        // given
        String userId = "dhdh";


        when(userRepository.findByUserId(userId)).thenReturn(null);

        // when
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> {
                    userDetailsService.loadUserByUsername(userId);
                });

        // then
    }

    @Test
    void 로그인성공() {
        // given
        String userId = "dhmk47@naver.com";

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(new User()));

        // when
        UserDetails user = userDetailsService.loadUserByUsername(userId);

        // then
        assertThat(user).isNotNull();
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