package com.project.instagram.domain.friend;

import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserDetail;
import com.project.instagram.domain.user.UserDetailRepository;
import com.project.instagram.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Test
    void 팔로우신청테스트() {
        // given
        User user = User.builder()
                .userId("dhmk47")
                .userPassword("1234")
                .userName("한대경")
                .userEmail("111")
                .userNickname("땡깡")
                .toFollowList(new ArrayList<>())
                .fromFollowList(new ArrayList<>())
                .build();

        UserDetail userDetail = UserDetail.builder()
                .userAddress("부산")
                .userPhoneNumber("010-4966-3160")
                .build();

        User user2 = User.builder()
                .userId("dhmkhk47")
                .userPassword("1111")
                .userName("둘대경")
                .userEmail("8888")
                .userNickname("깡땡")
                .toFollowList(new ArrayList<>())
                .fromFollowList(new ArrayList<>())
                .build();

        UserDetail userDetail2 = UserDetail.builder()
                .userAddress("울산")
                .userPhoneNumber("010-1111-2222")
                .build();

        User user3 = User.builder()
                .userId("ddddddd")
                .userPassword("9999")
                .userName("삼대경")
                .userEmail("197852")
                .userNickname("으악")
                .toFollowList(new ArrayList<>())
                .fromFollowList(new ArrayList<>())
                .build();

        UserDetail userDetail3 = UserDetail.builder()
                .userAddress("대구")
                .userPhoneNumber("010-3333-3333")
                .build();

        userDetailRepository.save(userDetail);
        userDetailRepository.save(userDetail2);
        userDetailRepository.save(userDetail3);

        user.setUserDetail(userDetail);
        user2.setUserDetail(userDetail2);
        user3.setUserDetail(userDetail3);
        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        Follow follow = Follow.builder()
                        .toUser(user)
                        .fromUser(user2)
                        .build();

        Follow follow2 = Follow.builder()
                        .toUser(user)
                        .fromUser(user3)
                        .build();


        user.getToFollowList().add(follow);
        user.getToFollowList().add(follow2);
//        user.getFromFollowList().add(follow);

        followRepository.save(follow);
        followRepository.save(follow2);
        // when
        Follow result = followRepository.findById(1L).orElseThrow(() -> {
            throw new RuntimeException();
        });

        List<Follow> result2 = followRepository.findByToUserCode(1L);
        // then
        assertThat(result.getToUser().getUserName()).isEqualTo("한대경");
        assertThat(result.getFromUser().getUserName()).isEqualTo("둘대경");

        assertThat(result2.size()).isEqualTo(2);
    }
}