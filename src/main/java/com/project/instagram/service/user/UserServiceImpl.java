package com.project.instagram.service.user;

import com.project.instagram.domain.friend.Follow;
import com.project.instagram.domain.friend.FollowRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserDetail;
import com.project.instagram.domain.user.UserDetailRepository;
import com.project.instagram.domain.user.UserRepository;
import com.project.instagram.web.dto.user.ReadUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    @Override
    public List<ReadUserResponseDto> searchUserList(String searchValue) {
        List<ReadUserResponseDto> userDtoList = null;

        String jpql = "select u from User u join fetch u.userDetail where u.userNickname like '%" + searchValue + "%' or u.userName like '%" + searchValue + "%'";

        List<User> userEntityList = entityManager.createQuery(jpql, User.class).getResultList();

        userDtoList = userEntityList.stream()
                .map(User::toUserDto)
                .collect(Collectors.toList());

        return userDtoList;
    }

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