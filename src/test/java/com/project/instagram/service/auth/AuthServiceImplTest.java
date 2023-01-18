package com.project.instagram.service.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    private AuthService authService;

    @BeforeEach
    public void init() {
        authService = new AuthServiceImpl();
    }

    @Test
    public void 로그인정보_불러오기_실패_NPE() {
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
    public void 로그인정보_불러오기_성공() {
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