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
    private final EntityManager entityManager;


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
        log.info("board: {}", board);

        return board;
    }

    @Override
    @Transactional
    public int loadBoardTotalCountByUserCode(Long userCode) {
        String jpql = "select count(*) from Board b where b.boardType.boardTypeCode = 1 and b.user.userCode = :userCode";
        return entityManager.createQuery(jpql, Integer.class).setParameter("userCode", userCode).getSingleResult();
    }
}