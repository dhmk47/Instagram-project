package com.project.instagram.service.friend;

import com.project.instagram.domain.friend.FollowRepository;
import com.project.instagram.web.dto.friend.ReadFollowListResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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