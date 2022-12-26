package com.project.instagram.service.user;

import com.project.instagram.domain.friend.Follow;
import com.project.instagram.domain.friend.FollowRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserDetail;
import com.project.instagram.domain.user.UserDetailRepository;
import com.project.instagram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

//    private final UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void test() {
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

        entityManager.persist(userDetail);
        entityManager.persist(userDetail2);
        entityManager.persist(userDetail3);

        user.setUserDetail(userDetail);
        user2.setUserDetail(userDetail2);
        user3.setUserDetail(userDetail3);
        entityManager.persist(user);
        entityManager.persist(user2);
        entityManager.persist(user3);

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
        user.getFromFollowList().add(follow);

        entityManager.persist(follow);
        entityManager.persist(follow2);
        
        User result2 = entityManager.find(User.class, 1L);

        log.info("user 1: {}", result2.getToFollowList());
        log.info("user 1: {}", result2.getFromFollowList());

    }

}