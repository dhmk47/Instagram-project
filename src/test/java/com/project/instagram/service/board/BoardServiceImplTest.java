package com.project.instagram.service.board;

import com.nimbusds.common.contenttype.ContentType;
import com.project.instagram.domain.board.*;
import com.project.instagram.domain.tag.LocationTagRepository;
import com.project.instagram.domain.tag.UserTagRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserDetail;
import com.project.instagram.handler.exception.file.FileException;
import com.project.instagram.service.tag.LocationTagService;
import com.project.instagram.service.tag.UserTagService;
import com.project.instagram.service.user.UserService;
import com.project.instagram.web.dto.board.CreateBoardRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

    @Mock
    private TypedQuery typedQuery;
    @Mock
    private EntityManager entityManager;
    @Mock
    private UserTagService userTagService;
    @Mock
    private BoardFileRepository boardFileRepository;
    @Mock
    private LocationTagService locationTagService;
    @Mock
    private User user;
    @Mock
    private BoardType boardType;

    private BoardService boardService;

    @BeforeEach
    void init() {
        boardService = new BoardServiceImpl(boardFileRepository, userTagService, locationTagService, entityManager);
    }

    @Test
    void 게시글작성() {
        // given
        CreateBoardRequestDto createBoardRequestDto = buildCreateBoardRequestDto();

        stubbingMockForCreateBoard(createBoardRequestDto);
        when(locationTagService.addLocationTag(any(Board.class), anyList())).thenReturn(true);
        when(userTagService.addUserTag(any(Board.class), anyList())).thenReturn(true);
        // when
        boolean status = boardService.createBoard(createBoardRequestDto);

        // then
        assertThat(status).isTrue();
    }

    @Test
    void 게시글작성_userTag() {
        // given
        CreateBoardRequestDto createBoardRequestDto = buildCreateBoardRequestDto();
        createBoardRequestDto.setUserCodeList(new ArrayList<>(Arrays.asList(1L, 2L)));

        stubbingMockForCreateBoard(createBoardRequestDto);
        when(locationTagService.addLocationTag(any(Board.class), anyList())).thenReturn(true);
        when(userTagService.addUserTag(any(Board.class), eq(createBoardRequestDto.getUserCodeList()))).thenReturn(true);

        // when
        boolean status = boardService.createBoard(createBoardRequestDto);

        // then
        assertThat(status).isTrue();
    }

    @Test
    void 게시글작성_locationTag() {
        // given
        CreateBoardRequestDto createBoardRequestDto = buildCreateBoardRequestDto();
        createBoardRequestDto.setLocationTagList(new ArrayList<>(Arrays.asList("개발", "백엔드")));

        stubbingMockForCreateBoard(createBoardRequestDto);
        when(locationTagService.addLocationTag(any(Board.class), eq(createBoardRequestDto.getLocationTagList()))).thenReturn(true);
        when(userTagService.addUserTag(any(Board.class), anyList())).thenReturn(true);

        // when
        boolean status = boardService.createBoard(createBoardRequestDto);

        // then
        assertThat(status).isTrue();
    }

    @Test
    void 게시글_파일_업로드_실패_허용되지않은_파일타입_ERROR_400() {
        // given
        MultipartFile multipartFile = new MockMultipartFile("testFile", "originalFileName", "file/zip", new byte[10]);
        CreateBoardRequestDto createBoardRequestDto = buildCreateBoardRequestDto();
        createBoardRequestDto.setBoardFileList(new ArrayList<>(Arrays.asList(multipartFile)));

        // when
        assertThatExceptionOfType(FileException.class)
                .isThrownBy(() -> boardService.createBoard(createBoardRequestDto));

        // then

    }

    @Test
    void 게시글_파일_업로드_성공() {
        // givne
        MultipartFile multipartFile = new MockMultipartFile("testFile", "originalFileName", "image/png", new byte[10]);
        CreateBoardRequestDto createBoardRequestDto = buildCreateBoardRequestDto();
        createBoardRequestDto.setBoardFileList(new ArrayList<>(Arrays.asList(multipartFile)));

        when(locationTagService.addLocationTag(any(Board.class), anyList())).thenReturn(true);
        when(userTagService.addUserTag(any(Board.class), anyList())).thenReturn(true);
        // when
        boolean status = boardService.createBoard(createBoardRequestDto);

        // then
        assertThat(status).isTrue();
    }

    @Test
    void 게시글조회() {
        // given

        // when
        Board board = boardService.loadBoardByBoardCode(1L);
        // then

    }

    @Test
    void 게시글_검색_COUNT_0개이상() {
        // given
        Long userCode = 1L;
        when(entityManager.createQuery(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(2);

        // when
        int totalCount = boardService.loadBoardTotalCountByUserCode(userCode);

        // then
        assertThat(totalCount).isEqualTo(2);
    }

    private CreateBoardRequestDto buildCreateBoardRequestDto() {
        return CreateBoardRequestDto.builder()
                .boardTypeCode(1L)
                .content("테스트입니다.")
                .userCode(8L)
                .disableCommentFlag(false)
                .hideViewAndLikeCountFlag(true)
                .locationTagList(Collections.emptyList())
                .userCodeList(Collections.emptyList())
                .boardFileList(Collections.emptyList())
                .build();
    }

    private void stubbingMockForCreateBoard(CreateBoardRequestDto createBoardRequestDto) {
        when(entityManager.find(BoardType.class, createBoardRequestDto.getBoardTypeCode())).thenReturn(boardType);
        when(entityManager.find(User.class, createBoardRequestDto.getUserCode())).thenReturn(user);
    }
}