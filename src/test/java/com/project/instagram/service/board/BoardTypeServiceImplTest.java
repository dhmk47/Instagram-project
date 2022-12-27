package com.project.instagram.service.board;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.BoardType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardTypeServiceImplTest {

    @Autowired
    private BoardTypeService boardTypeService;

    @Test
    void 게시글타입생성() {
        // given
        BoardType boardType = BoardType.builder()
                .boardTypeName("게시글")
                .build();

        // when
        boardTypeService.createBoardType(boardType);

        // then

    }

    @Test
    void 게시글타입으로_게시글리스트_조회() {
        // given

        // when
        List<Board> boardList = boardTypeService.loadAllBoardByBoardType(1L);

        // then
        
    }
}