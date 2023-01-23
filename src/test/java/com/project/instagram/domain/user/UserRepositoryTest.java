package com.project.instagram.domain.user;

import com.project.instagram.domain.board.Board;
import com.project.instagram.web.dto.user.ReadUserProfilelInformationResponseDto;
import com.project.instagram.web.dto.user.ReadUserRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 유저생성성공() {
        // given
        User user = User.builder()
                .userId("dhmk47@gmail.com")
                .userPassword("1234")
                .userName("한대경")
                .userEmail("dhmk47@naver.com")
                .userNickname("dae._gyeong")
                .build();

        UserDetail userDetail = UserDetail.builder()
                .userAddress("부산")
                .build();


        // when
        userDetailRepository.save(userDetail);

        user.setUserDetail(userDetail);

        userRepository.save(user);

        user = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> {
            throw new UsernameNotFoundException("아이디 혹은 비밀번호가 잘못되었습니다.");
        });

        // then
        assertThat(user.getUserName()).isEqualTo("한대경");
    }

    @Test
    void 로그인_유저정보조회실패_아이디_없음() {
        // given
        ReadUserRequestDto readUserRequestDto = ReadUserRequestDto.builder()
                .userId("dhmk47123")
                .userPassword("123")
                .build();

        User readUserEntity = readUserRequestDto.toUserEntity();

        // then
        User user = userRepository.findByUserId(readUserEntity.getUserId()).orElse(null);

        // when
        assertThat(user).isNull();
    }

    @Test
    void 로그인_유저정보조회성공() {
        // given
        ReadUserRequestDto readUserRequestDto = ReadUserRequestDto.builder()
                .userId("dhmk47@naver.com")
                .userPassword("Password123!")
                .build();

        User readUserEntity = readUserRequestDto.toUserEntity();

        // when
        User user = userRepository.findByUserId(readUserEntity.getUserId()).orElse(null);

        user.getUserDetail().getUserProfileImage();
        // then
        assertThat(user).isNotNull();
    }

    @Test
    void 유저검색성공() {
        // given
        String searchValue = "대";
        String jpql = "select u from User u join fetch u.userDetail where u.userNickname like '%" + searchValue + "%' or u.userName like '%" + searchValue + "%'";

        // when
        List<User> userList = entityManager.createQuery(jpql, User.class).getResultList();

        // then
        assertThat(userList).hasSizeGreaterThan(0);
    }

    @Test
    void User_Entity_연관관계_정보_조회() {
        // given
        String userNickname = "땡깡";
        String jpql = "select distinct u from User u join fetch u.boardList join u.followList join u.fromFollowList where u.userNickname = :userNickname";

        // when
        User user = entityManager.createQuery(jpql, User.class).setParameter("userNickname", userNickname).getSingleResult();
//        User user = entityManager.createQuery(jpql, User.class).setParameter("userCode", userCode).getSingleResult();
        ReadUserProfilelInformationResponseDto userCountInformationDto = ReadUserProfilelInformationResponseDto.builder()
                .userNickname(user.getUserNickname())
                .userName(user.getUserName())
                .boardList(user.getBoardList().stream()
                        .map(Board::toBoardDto)
                        .collect(Collectors.toList()))
                .introduceContent(user.getUserDetail().getIntroduceContent())
                .profileImage(user.getUserDetail().getUserProfileImage())
                .boardCount(user.getBoardList().size())
                .followingCount(user.getFollowList().size())
                .followerCount(user.getFromFollowList().size())
                .build();
        // then
//        assertThat(user).isNotNull();
    }
}