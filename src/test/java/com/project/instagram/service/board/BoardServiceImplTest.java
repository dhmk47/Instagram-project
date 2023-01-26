package com.project.instagram.service.board;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.BoardRepository;
import com.project.instagram.domain.board.BoardType;
import com.project.instagram.domain.board.BoardTypeRepository;
import com.project.instagram.domain.tag.LocationTagRepository;
import com.project.instagram.domain.tag.UserTagRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserDetail;
import com.project.instagram.web.dto.board.CreateBoardRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

    @Mock
    private TypedQuery typedQuery;
    @Mock
    private EntityManager entityManager;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private BoardTypeRepository boardTypeRepository;
    @Mock
    private UserTagRepository userTagRepository;
    @Mock
    private LocationTagRepository locationTagRepository;
    @Mock
    private User user;
    @Mock
    private BoardType boardType;

    private BoardService boardService;

    @BeforeEach
    void init() {
        boardService = new BoardServiceImpl(boardRepository, boardTypeRepository, userTagRepository, locationTagRepository, entityManager);
    }

    @Test
    void 게시글작성() {
        // given
        CreateBoardRequestDto createBoardRequestDto = buildCreateBoardRequestDto();

        stubbingMockForCreateBoard(createBoardRequestDto);
        // when
        boolean status = boardService.createBoard(createBoardRequestDto);

        // then
        assertThat(status).isTrue();
    }

    @Test
    void 게시글작성_userTag() {
        // given
        CreateBoardRequestDto createBoardRequestDto = buildCreateBoardRequestDto();
        createBoardRequestDto.setUserTagList(new ArrayList<>(Arrays.asList("땡깡", "깡땡")));
        User user1 = User.builder()
                .userId("dhmk47@naver.com")
                .userName("한대경")
                .userPassword("123")
                .userNickname("땡깡")
                .userDetail(new UserDetail())
                .build();
        User user2 = User.builder()
                .userId("dhmk47@gmail.com")
                .userName("둘대경")
                .userPassword("333")
                .userNickname("깡땡")
                .userDetail(new UserDetail())
                .build();

        stubbingMockForCreateBoard(createBoardRequestDto);
        when(entityManager.createQuery(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(new ArrayList(Arrays.asList(user1, user2)));
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
                .userTagList(Collections.emptyList())
                .build();
    }

    private void stubbingMockForCreateBoard(CreateBoardRequestDto createBoardRequestDto) {
        when(entityManager.find(BoardType.class, createBoardRequestDto.getBoardTypeCode())).thenReturn(boardType);
        when(entityManager.find(User.class, createBoardRequestDto.getUserCode())).thenReturn(user);
    }
}