package com.project.instagram.web.controller.api.user;

import com.google.gson.Gson;
import com.project.instagram.handler.exception.CustomExceptionHandler;
import com.project.instagram.handler.exception.user.UserException;
import com.project.instagram.handler.exception.user.UserExceptionResult;
import com.project.instagram.service.user.UserService;
import com.project.instagram.web.dto.user.ReadUserProfileInformationResponseDto;
import com.project.instagram.web.dto.user.ReadUserDetailResponseDto;
import com.project.instagram.web.dto.user.ReadUserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    @InjectMocks
    private UserRestController userRestController;
    private Gson gson;
    private MockMvc mockMvc;
    private CustomExceptionHandler customExceptionHandler;
    @Mock
    private UserService userService;

    @BeforeEach
    void init() {
        gson = new Gson();
        customExceptionHandler = new CustomExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController)
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
    void 검색결과_크기0인_배열반환() throws Exception {
        // given
        String url = "/api/v1/user/list/search";
        String searchValue = "없는값";

        when(userService.searchUserList(searchValue)).thenReturn(Collections.emptyList());

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("searchValue", searchValue)
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print());
        resultActions.andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void 검색결과_크기0이상_배열반환() throws Exception {
        // given
        String url = "/api/v1/user/list/search";
        String searchValue = "대";
        List<ReadUserResponseDto> userDtoList = new ArrayList<>();
        ReadUserResponseDto readUserResponseDto = ReadUserResponseDto.builder().build();
        ReadUserDetailResponseDto readUserDetailResponseDto = ReadUserDetailResponseDto.builder().build();
        readUserResponseDto.setUserDetail(readUserDetailResponseDto);
        userDtoList.add(readUserResponseDto);

        when(userService.searchUserList(searchValue)).thenReturn(userDtoList);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("searchValue", searchValue)
        );

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print());
        resultActions.andExpect(jsonPath("$.data").isNotEmpty());
        resultActions.andExpect(jsonPath("$.data[0].userCode").isEmpty());
    }

    @Test
    void User_Entity_연관관계_정보_조회_실패_닉네임_없음_ERROR_400() throws Exception {
        // givne
        String url = "/api/v1/user/profile/detail";
        String userNickname = "없는값";

        when(userService.getUserDetailCountInformation(userNickname)).thenThrow(new UserException(UserExceptionResult.NO_RESULT_USER_BY_USER_NICKNAME));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("userNickname", userNickname)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void User_Entity_연관관계_정보_조회() throws Exception {
        // given
        String url = "/api/v1/user/profile/detail";
        String userNickname = "땡깡";

        ReadUserProfileInformationResponseDto userDetailCountResponseDto =
                ReadUserProfileInformationResponseDto.builder()
                        .userName("한대경")
                        .userNickname(userNickname)
                        .introduceContent("안녕하세요.")
                        .profileImage(null)
                        .boardCount(2)
                        .followerCount(3)
                        .followingCount(4)
                        .build();

        when(userService.getUserDetailCountInformation(userNickname)).thenReturn(userDetailCountResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("userNickname", userNickname)
        );

        // then
        resultActions.andDo(MockMvcResultHandlers.print());
        resultActions.andExpect(jsonPath("$.data.userName").value("한대경"));
        resultActions.andExpect(jsonPath("$.data.boardCount").value(2));
        resultActions.andExpect(jsonPath("$.data.followerCount").value(3));
        resultActions.andExpect(jsonPath("$.data.followingCount").value(4));
    }
}