package com.project.instagram.service.board;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.BoardType;
import com.project.instagram.domain.board.BoardTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardTypeServiceImpl implements BoardTypeService {

    private final BoardTypeRepository boardTypeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean createBoardType(BoardType boardType) {
        boardTypeRepository.save(boardType);
        return true;
    }

    @Transactional
    @Override
    public List<Board> loadAllBoardByBoardType(Long boardTypeCode) {
        BoardType boardType = entityManager.find(BoardType.class, boardTypeCode);
        log.info("boardList: {}", boardType.getBoardList());

        return boardType.getBoardList();
    }
}