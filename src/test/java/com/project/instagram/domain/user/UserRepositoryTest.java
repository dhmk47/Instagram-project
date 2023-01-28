package com.project.instagram.domain.user;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.BoardRepository;
import com.project.instagram.domain.board.BoardType;
import com.project.instagram.domain.board.BoardTypeRepository;
import com.project.instagram.domain.tag.UserTag;
import com.project.instagram.web.dto.user.ReadUserProfileInformationResponseDto;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.COLLECTION;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardTypeRepository boardTypeRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 유저생성성공() {
        // given
        User user = User.builder()
                .userId("dhmk47@gmail.com")
                .userPassword("1234")
                .userName("구대경")
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
        assertThat(user.getUserName()).isEqualTo("구대경");
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
        String userNickname = "dae_.test";
        String jpql = "select distinct u from User u left join u.boardList left join u.followList left join u.fromFollowList left join u.savedBoardList left join u.taggedList where u.userNickname = :userNickname";
        // when
        User user = entityManager.createQuery(jpql, User.class).setParameter("userNickname", userNickname).getSingleResult();

//        User user = entityManager.createQuery(jpql, User.class).setParameter("userCode", userCode).getSingleResult();
        ReadUserProfileInformationResponseDto userProfileInformationResponseDto = ReadUserProfileInformationResponseDto.builder()
                .userNickname(user.getUserNickname())
                .userName(user.getUserName())
                .boardList(user.getBoardList().stream()
                        .map(Board::toBoardDto)
                        .collect(Collectors.toList()))
                .savedBoardList(user.getSavedBoardList().stream()
                        .map(savedBoard -> savedBoard.getBoard().toBoardDto())
                        .collect(Collectors.toList()))
                .taggedBoardList(user.getTaggedList().stream()
                        .map(taggedBoard -> taggedBoard.getBoard().toBoardDto())
                        .collect(Collectors.toList()))
                .introduceContent(user.getUserDetail().getIntroduceContent())
                .profileImage(user.getUserDetail().getUserProfileImage())
                .boardCount(user.getBoardList().size())
                .followingCount(user.getFollowList().size())
                .followerCount(user.getFromFollowList().size())
                .build();

        // then
        assertThat(userProfileInformationResponseDto.getBoardList().size()).isEqualTo(1);
        assertThat(userProfileInformationResponseDto.getSavedBoardList().size()).isEqualTo(1);
        assertThat(userProfileInformationResponseDto.getTaggedBoardList().size()).isEqualTo(1);
    }

    @Test
    void userTag생성을_위한_user조회성공() {
        // given
        int index = 0;
        String jpql = "select u from User u where u.userNickname in (";
        List<String> userTagList = new ArrayList<>(Arrays.asList("땡깡", "깡땡"));

        for(String userNickname : userTagList) {
            jpql += "'" + userNickname + "'" + (index != userTagList.size() - 1 ? ", " : ")");
            index++;
        }

        // when
        List<User> userList = entityManager.createQuery(jpql, User.class).getResultList();

        // then
        assertThat(userList).hasSize(userTagList.size());
    }
}