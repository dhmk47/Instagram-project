package com.project.instagram.service.board;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.BoardRepository;
import com.project.instagram.domain.board.BoardTypeRepository;
import com.project.instagram.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

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

    private BoardService boardService;

    @BeforeEach
    void init() {
        boardService = new BoardServiceImpl(boardRepository, boardTypeRepository, entityManager);
    }

    @Test
    void 게시글작성() {
        // given
        User user = entityManager.find(User.class, 1L);
        Board board = Board.builder()
                .content("한번더 게시글 작성 테스트")
                .build();
        board.setUser(user);

        boardService.createBoard(board, 1L);
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
}