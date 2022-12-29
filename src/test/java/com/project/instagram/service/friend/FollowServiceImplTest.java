package com.project.instagram.service.friend;

import com.project.instagram.domain.friend.Follow;
import com.project.instagram.domain.friend.FollowRepository;
import com.project.instagram.domain.user.UserDetailRepository;
import com.project.instagram.domain.user.UserRepository;
import com.project.instagram.service.user.UserService;
import com.project.instagram.web.dto.friend.ReadFollowListResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FollowServiceImplTest {

    @Autowired
    private FollowService followService;


    @Test
    void 팔로우신청() {
        followService.addFollow();
    }

    @Test
    void 유저정보에_팔로워정보_찾기() {
        // given


        // when
        List<ReadFollowListResponseDto> followList = followService.loadFollowUser();


        // then
        assertThat(followList.size()).isEqualTo(2);
    }
}