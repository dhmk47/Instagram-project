package com.project.instagram.service.storage;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.storage.Storage;
import com.project.instagram.domain.storage.StorageRepository;
import com.project.instagram.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void saveBoardInStorage(Long userCode, Long boardCode) {
        User user = entityManager.find(User.class, userCode);
        Board board = entityManager.find(Board.class, boardCode);

        Storage storage = Storage.builder().build();

        storage.setUser(user);
        storage.setBoard(board);

        entityManager.persist(storage);
    }
}