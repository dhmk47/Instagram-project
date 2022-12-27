package com.project.instagram.service.board;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.BoardRepository;
import com.project.instagram.domain.board.BoardType;
import com.project.instagram.domain.board.BoardTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardTypeRepository boardTypeRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    @Override
    public Long createBoard(Board board, Long boardTypeCode) {
        BoardType boardType = entityManager.find(BoardType.class, boardTypeCode);

        boardType.getBoardList().add(board);
        board.setBoardType(boardType);

        entityManager.persist(board);

        return board.getBoardCode();
    }

    @Transactional
    @Override
    public Board loadBoardByBoardCode(Long boardCode) {
        Board board = entityManager.find(Board.class, boardCode);

        log.info("board: {}", board.getBoardType());
        log.info("board: {}", board.getStorage());
        return board;
    }
}