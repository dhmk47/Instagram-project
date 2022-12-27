package com.project.instagram.service.board;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 게시글작성() {
        // given
        User user = entityManager.find(User.class, 1L);
        Board board = Board.builder()
                .content("게시글 작성 테스트")
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
}