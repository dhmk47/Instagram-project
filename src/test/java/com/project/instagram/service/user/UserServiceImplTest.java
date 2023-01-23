package com.project.instagram.service.user;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.BoardType;
import com.project.instagram.domain.friend.Follow;
import com.project.instagram.domain.friend.FollowRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserDetail;
import com.project.instagram.domain.user.UserRepository;
import com.project.instagram.web.dto.user.ReadUserProfilelInformationResponseDto;
import com.project.instagram.web.dto.user.ReadUserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FollowRepository followRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private TypedQuery typedQuery;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl(userRepository, followRepository, entityManager);
    }

    @Test
    void 유저검색성공_검색건수_0개() {
        // given
        String searchValue = "없는값";
        when(entityManager.createQuery(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // when
        List<ReadUserResponseDto> userList = userService.searchUserList(searchValue);

        // then
        assertThat(userList).hasSize(0);
    }

    @Test
    void 유저검색성공_검색선수_0개이상() {
        // given
        String searchValue = "대";
        List<User> returnUserList = new ArrayList();

        UserDetail userDetail = new UserDetail();
        User returnUser = new User();

        returnUser.setUserDetail(userDetail);
        returnUserList.add(returnUser);

        when(entityManager.createQuery(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(returnUserList);

        // when
        List<ReadUserResponseDto> userList = userService.searchUserList(searchValue);

        // then
        assertThat(userList).hasSizeGreaterThan(0);
    }

    @Test
    void User_Entity_연관관계_정보_조회() {
        // given
        String userNickname = "땡깡";
        UserDetail userDetail = new UserDetail();
        User user = new User();
        BoardType boardType = new BoardType();
        boardType.setBoardTypeCode(1L);
        Board board = Board.builder()
                .boardCode(1L)
                .content("게시글!")
                .user(user)
                .boardType(boardType)
                .boardFileList(Collections.emptyList())
                .build();
        user.setUserDetail(userDetail);
        user.setUserName("한대경");
        user.setUserNickname(userNickname);
        user.getUserDetail().setIntroduceContent("안녕하세요.");
        user.setBoardList(new ArrayList<>(Arrays.asList(board)));
        user.setFollowList(new ArrayList<>(Arrays.asList(new Follow())));
        user.setFromFollowList(new ArrayList<>(Arrays.asList(new Follow(), new Follow(), new Follow())));

        when(entityManager.createQuery(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(user);

        // when
        ReadUserProfilelInformationResponseDto userDetailCountInformationDto = userService.getUserDetailCountInformation(userNickname);

        // then
        assertThat(userDetailCountInformationDto.getBoardCount()).isEqualTo(1);
        assertThat(userDetailCountInformationDto.getFollowerCount()).isEqualTo(3);
        assertThat(userDetailCountInformationDto.getFollowingCount()).isEqualTo(1);
        assertThat(userDetailCountInformationDto.getUserName()).isEqualTo("한대경");
    }
}