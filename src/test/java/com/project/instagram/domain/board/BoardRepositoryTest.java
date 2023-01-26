package com.project.instagram.domain.board;

import com.project.instagram.domain.tag.LocationTag;
import com.project.instagram.domain.tag.LocationTagRepository;
import com.project.instagram.domain.tag.UserTag;
import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserDetailRepository;
import com.project.instagram.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private BoardTypeRepository boardTypeRepository;
    @Autowired
    private LocationTagRepository locationTagRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 게시글생성성공() {
        // given
        User user = entityManager.find(User.class, 8L);

        BoardType boardType = BoardType.builder()
                .boardTypeName("게시글!!!")
                .build();

        boardTypeRepository.save(boardType);

        Board board = Board.builder()
                .hideViewAndLikeCountFlag(0)
                .disableCommentFlag(0)
                .user(user)
                .boardType(boardType)
                .build();

        boardRepository.save(board);


        // when
        String jpql = "select b from Board b where b.boardCode = :boardCode";
        Board boardResult = entityManager.createQuery(jpql, Board.class).setParameter("boardCode", board.getBoardCode()).getResultList().get(0);

        // then
        assertThat(boardResult.getBoardType().getBoardTypeName()).isEqualTo("게시글!!!");
    }

    @Test
    void 게시글생성성공_locationTag() {
        // given
        User user = entityManager.find(User.class, 8L);
        BoardType boardType = entityManager.find(BoardType.class, 1L);
        Board newBoard = Board.builder()
                .boardType(boardType)
                .disableCommentFlag(0)
                .hideViewAndLikeCountFlag(0)
                .locationTagList(new ArrayList<>())
                .user(user)
                .build();
        LocationTag locationTag = LocationTag.builder()
                .tagName("개발")
                .board(newBoard)
                .build();

        newBoard.getLocationTagList().add(locationTag);
        // when
        entityManager.persist(newBoard);
        entityManager.persist(locationTag);

        String jpql = "select b from Board b join fetch b.locationTagList where b.boardCode = :boardCode";
        Board boardResult = entityManager.createQuery(jpql, Board.class).setParameter("boardCode", newBoard.getBoardCode()).getSingleResult();

        // then
        assertThat(boardResult.getBoardCode()).isEqualTo(newBoard.getBoardCode());
        assertThat(boardResult.getUser()).isEqualTo(user);
        assertThat(boardResult.getLocationTagList().size()).isEqualTo(1);
        assertThat(boardResult.getLocationTagList().get(0).getTagName()).isEqualTo("개발");
    }

    @Test
    void 게시글생성성공_userTag() {
        // given
        User toUser = entityManager.find(User.class, 8L);
        User fromUser = entityManager.find(User.class, 1L);
        BoardType boardType = entityManager.find(BoardType.class, 1L);
        Board newBoard = Board.builder()
                .boardType(boardType)
                .disableCommentFlag(0)
                .hideViewAndLikeCountFlag(0)
                .userTagList(new ArrayList<>())
                .user(fromUser)
                .build();
        UserTag userTag = UserTag.builder()
                .toUser(toUser)
                .fromUser(fromUser)
                .board(newBoard)
                .build();

        newBoard.getUserTagList().add(userTag);
        // when
        entityManager.persist(newBoard);
        entityManager.persist(userTag);

        String jpql = "select b from Board b join fetch b.userTagList where b.boardCode = :boardCode";
        Board boardResult = entityManager.createQuery(jpql, Board.class).setParameter("boardCode", newBoard.getBoardCode()).getSingleResult();

        // then
        assertThat(boardResult.getBoardCode()).isEqualTo(newBoard.getBoardCode());
        assertThat(boardResult.getUserTagList().size()).isEqualTo(1);
        assertThat(boardResult.getUserTagList().get(0).getToUser()).isEqualTo(toUser);
    }

    @Test
    void 게시글생성성공_userTag_locationTag() {
        // given
        User toUser = entityManager.find(User.class, 8L);
        User fromUser = entityManager.find(User.class, 1L);
        BoardType boardType = entityManager.find(BoardType.class, 1L);
        Board newBoard = Board.builder()
                .boardType(boardType)
                .disableCommentFlag(0)
                .hideViewAndLikeCountFlag(0)
                .userTagList(new ArrayList<>())
                .locationTagList(new ArrayList<>())
                .user(fromUser)
                .build();
        UserTag userTag = UserTag.builder()
                .toUser(toUser)
                .fromUser(fromUser)
                .board(newBoard)
                .build();
        LocationTag locationTag = LocationTag.builder()
                .tagName("백엔드")
                .board(newBoard)
                .build();

        newBoard.getUserTagList().add(userTag);
        newBoard.getLocationTagList().add(locationTag);
        // when
        entityManager.persist(newBoard);
        entityManager.persist(userTag);
        entityManager.persist(locationTag);

        String jpql = "select b from Board b join fetch b.userTagList left join b.locationTagList where b.boardCode = :boardCode";
        Board boardResult = entityManager.createQuery(jpql, Board.class).setParameter("boardCode", newBoard.getBoardCode()).getSingleResult();

        // then
        assertThat(boardResult.getUserTagList().size()).isEqualTo(1);
        assertThat(boardResult.getLocationTagList().size()).isEqualTo(1);
        assertThat(boardResult.getLocationTagList().get(0).getTagName()).isEqualTo(locationTag.getTagName());
    }

    @Test
    void 게시글_COUNT_조회성공() {
        // given
        String jpql = "select count(*) from Board b where b.boardType.boardTypeCode = 1 and b.user.userCode = :userCode";
        Long userCode = 1L;

        // when
        Long totalCount = entityManager.createQuery(jpql, Long.class)
                .setParameter("userCode", userCode)
                .getSingleResult();

        // then
        assertThat(totalCount).isEqualTo(1);
    }

}