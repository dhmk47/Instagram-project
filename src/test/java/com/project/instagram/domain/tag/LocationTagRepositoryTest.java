package com.project.instagram.domain.tag;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.BoardType;
import com.project.instagram.domain.user.User;
import com.project.instagram.web.dto.locationTag.ReadLocationTagResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.stream.Location;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LocationTagRepositoryTest {
    @PersistenceContext
    private EntityManager entityManager;


    @Test
    void locationTag_등록성공() {
        // given
        Board board = Board.builder()
                .boardType(entityManager.find(BoardType.class, 1L))
                .user(entityManager.find(User.class, 8L))
                .disableCommentFlag(0)
                .hideViewAndLikeCountFlag(0)
                .build();

        entityManager.persist(board);

        LocationTag locationTag = LocationTag.builder()
                .tagName("백엔드")
                .board(board)
                .build();

        entityManager.persist(locationTag);

        // then
        String searchValue = "엔드";
        String jpql = "select l from LocationTag l where l.tagName like '%" + searchValue + "%'";
        LocationTag locationTagResult = entityManager.createQuery(jpql, LocationTag.class).getResultList().get(0);

        // when
        assertThat(locationTagResult.getTagName()).isEqualTo("백엔드");
    }

    @Test
    void locationTag_불러오기_성공() {
        // given
        Board board = Board.builder()
                .boardType(entityManager.find(BoardType.class, 1L))
                .user(entityManager.find(User.class, 8L))
                .disableCommentFlag(0)
                .hideViewAndLikeCountFlag(0)
                .build();

        entityManager.persist(board);

        LocationTag locationTag = LocationTag.builder()
                .tagName("서면")
                .board(board)
                .build();

        entityManager.persist(locationTag);

        // then
        String searchValue = "서";
        String jpql = "select new com.project.instagram.web.dto.locationTag.ReadLocationTagResponseDto(l.tagCode, l.tagName, count(l.tagName)) from LocationTag l where l.tagName like '%" + searchValue + "%' group by l.tagName";
        ReadLocationTagResponseDto locationTagResult = entityManager.createQuery(jpql, ReadLocationTagResponseDto.class).getResultList().get(0);

        // when
        assertThat(locationTagResult.getTotalCount()).isEqualTo(2L);
    }
}