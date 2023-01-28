package com.project.instagram.domain.tag;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.BoardType;
import com.project.instagram.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserTagRepositoryTest {

    @Autowired
    private UserTagRepository userTagRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void userTagList_saveAll_호출성공() {
        // given
        User fromUser = entityManager.find(User.class, 1L);
        Board board = Board.builder()
                .boardType(entityManager.find(BoardType.class, 1L))
                .user(fromUser)
                .hideViewAndLikeCountFlag(0)
                .disableCommentFlag(0)
                .build();

        UserTag userTag1 = UserTag.builder()
                .fromUser(fromUser)
                .toUser(entityManager.find(User.class, 8L))
                .board(board)
                .build();

        UserTag userTag2 = UserTag.builder()
                .fromUser(fromUser)
                .toUser(entityManager.find(User.class, 2L))
                .board(board)
                .build();

        List<UserTag> userTagList = new ArrayList<>(Arrays.asList(userTag1, userTag2));

        // when
        entityManager.persist(board);
        userTagRepository.saveAll(userTagList);

        List<UserTag> userTagResultList = userTagRepository.findAll();

        // then
        assertThat(userTagResultList).hasSize(2);
    }
}