package com.project.instagram.domain.user;

import com.project.instagram.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Test
    public void 유저생성테스트() {
        // given
        User user = User.builder()
                .userId("dhmk47")
                .userPassword("1234")
                .userName("한대경")
                .userEmail("111")
                .userNickname("땡깡")
                .build();

        UserDetail userDetail = UserDetail.builder()
                .userAddress("부산")
                .userPhoneNumber("010-4966-3160")
                .build();

        userDetailRepository.save(userDetail);

        user.setUserDetail(userDetail);

        // when
        userRepository.save(user);
        User result = userRepository.findById(1L).orElseThrow(() -> {
            throw new RuntimeException();
        });

        // then
        assertThat(result.getUserName()).isEqualTo("한대경");

    }

    @Test
    void 유저조인테스트() {
        // given

        // when
        User result = userRepository.findById(1L).orElseThrow(() -> {
            throw new RuntimeException();
        });

        UserDetail userDetail = userDetailRepository.findById(1L).orElseThrow(() -> {
            throw new RuntimeException();
        });

        // then
        assertThat(result.getUserDetail().getUserAddress()).isEqualTo("부산");
    }
}