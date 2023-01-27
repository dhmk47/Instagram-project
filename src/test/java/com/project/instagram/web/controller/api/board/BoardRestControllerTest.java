package com.project.instagram.web.controller.api.board;

import com.google.gson.Gson;
import com.project.instagram.handler.exception.CustomExceptionHandler;
import com.project.instagram.service.board.BoardService;
import com.project.instagram.web.dto.board.CreateBoardRequestDto;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BoardRestControllerTest {

    @InjectMocks
    private BoardRestController boardRestController;
    @Mock
    private BoardService boardService;
    private Gson gson;
    private MockMvc mockMvc;
    private CustomExceptionHandler customExceptionHandler;

    @BeforeEach
    void init() {
        gson = new Gson();
        customExceptionHandler = new CustomExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(boardRestController)
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
    void 게시글작성_API_호출실패_Null값_들어옴() throws Exception {
        // given
        String url = "/api/v1/board";
        CreateBoardRequestDto createBoardRequestDto = CreateBoardRequestDto.builder()
                .hideViewAndLikeCountFlag(false)
                .disableCommentFlag(false)
                .userCode(null)
                .boardTypeCode(1L)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(createBoardRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andDo(MockMvcResultHandlers.print());
        resultActions.andExpect(jsonPath("$.message").value("Argument Validation Error"));
    }

    @Test
    void 게시글작성_API_호출성공() throws Exception {
        // given
        String url = "/api/v1/board";
        boolean status = true;
        CreateBoardRequestDto createBoardRequestDto = CreateBoardRequestDto.builder()
                .content("게시글")
                .hideViewAndLikeCountFlag(false)
                .disableCommentFlag(false)
                .userCode(8L)
                .boardTypeCode(1L)
                .build();

        when(boardService.createBoard(createBoardRequestDto)).thenReturn(status);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(createBoardRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.data").value(status));
    }

    @Test
    void 게시글_전체_COUNT_가져오는_API_호출성공() throws Exception {
        // given
        Long userCode = 1L;
        String url = "/api/v1/board/count/user/" + userCode;

        when(boardService.loadBoardTotalCountByUserCode(userCode)).thenReturn(2);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andDo(MockMvcResultHandlers.print());
        resultActions.andExpect(jsonPath("$.data").value(2));
    }
}