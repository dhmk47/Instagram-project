package com.project.instagram.domain.board;

import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserDetail;
import com.project.instagram.domain.user.UserDetailRepository;
import com.project.instagram.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

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

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 게시글생성성공() {
        // given
        Board board = Board.builder()
                .content("테스트 게시글")
                .build();

        BoardType boardType = BoardType.builder()
                .boardTypeName("게시글")
                .build();

        User user = User.builder()
                .userId("dhfdhksaf")
                .userPassword("1234")
                .userName("한대경")
                .userEmail("111")
                .userNickname("땡깡")
                .build();

        UserDetail userDetail = UserDetail.builder()
                .userAddress("부산")
                .build();

        userDetailRepository.save(userDetail);
        user.setUserDetail(userDetail);
        userRepository.save(user);

        boardTypeRepository.save(boardType);


        board.setBoardType(boardType);
        board.setUser(user);
        boardRepository.save(board);

        // when
        Board result = entityManager.find(Board.class, board.getBoardCode());

        // then
        assertThat(result.getBoardType().getBoardTypeName()).isEqualTo("게시글");
        assertThat(result.getContent()).isEqualTo("테스트 게시글");
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
        assertThat(totalCount).isEqualTo(2);
    }

}