package com.project.instagram.web.controller.api;

import com.google.gson.Gson;
import com.project.instagram.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(authRestController)
                .build();
    }

    @Test
    public void gson_mockMvc_isNotNull() {

        // then
        assertThat(gson).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void map객체가_null_NPE_ERROR_500() throws Exception {
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
    public void map객체가_제대로_전달됨() throws Exception{
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