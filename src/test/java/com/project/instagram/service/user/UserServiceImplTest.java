package com.project.instagram.service.user;

import com.project.instagram.domain.friend.FollowRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserDetail;
import com.project.instagram.domain.user.UserRepository;
import com.project.instagram.web.dto.user.ReadUserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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
        when(typedQuery.getResultList()).thenReturn(new ArrayList());

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
}