package com.project.instagram.service.tag;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.tag.UserTagRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTagServiceImplTest {

    @Mock
    private UserService userService;
    @Mock
    private UserTagRepository userTagRepository;
    @Mock
    private EntityManager entityManager;
    private UserTagService userTagService;

    @BeforeEach
    void init() {
        userTagService = new UserTagServiceImpl(userService, userTagRepository, entityManager);
    }

    @Test
    void userTag_등록성공() {
        // given
        Board mockBoard = mock(Board.class);
        List<Long> userCodeList = new ArrayList<>(Arrays.asList(1L, 2L));
        List<User> userList = new ArrayList<>(Arrays.asList(mock(User.class), mock(User.class)));

        when(userService.getUserListByUserCode(userCodeList)).thenReturn(userList);
        when(mockBoard.getUser()).thenReturn(any(User.class));

        // when
        userTagService.addUserTag(mockBoard, userCodeList);

        // then

    }
}