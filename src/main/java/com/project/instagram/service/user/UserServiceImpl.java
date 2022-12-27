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
    public void addFollow() {
        User toUser = entityManager.find(User.class, 2L);
        User fromUser = entityManager.find(User.class, 3L);

        Follow follow = Follow.builder()
                .build();

        follow.setFromUser(fromUser);
        follow.setToUser(toUser);

        entityManager.persist(follow);
    }

    @Transactional
    @Override
    public void loadFollowUser() {
        User user = entityManager.find(User.class, 1L);

        log.info("user: {}", user);
//        log.info("to: {}", user.getFollowList());
//        log.info("from: {}", user.getFromFollowList());

    }
}