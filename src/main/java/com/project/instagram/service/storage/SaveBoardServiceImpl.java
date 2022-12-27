package com.project.instagram.service.storage;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.storage.SaveBoard;
import com.project.instagram.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaveBoardServiceImpl implements SaveBoardService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insertSaveBoard(Long userCode, Long boardCode) {
        User user = entityManager.find(User.class, userCode);
        Board board = entityManager.find(Board.class, boardCode);

        SaveBoard saveBoard = SaveBoard.builder()
                .user(user)
                .board(board)
                .build();

        entityManager.persist(saveBoard);
    }
}