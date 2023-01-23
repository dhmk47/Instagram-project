package com.project.instagram.service.user;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.friend.Follow;
import com.project.instagram.domain.friend.FollowRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserRepository;
import com.project.instagram.web.dto.user.ReadUserResponseDto;
import com.project.instagram.web.dto.user.ReadUserProfilelInformationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public ReadUserProfilelInformationResponseDto getUserDetailCountInformation(String userNickname) {
        String jpql = "select distinct u from User u join fetch u.boardList join u.followList join u.fromFollowList where u.userNickname = :userNickname";
        User user = entityManager.createQuery(jpql, User.class).setParameter("userNickname", userNickname).getSingleResult();

        return ReadUserProfilelInformationResponseDto.builder()
                .userName(user.getUserName())
                .userNickname(user.getUserNickname())
                .boardList(user.getBoardList().stream()
                        .map(Board::toBoardDto)
                        .collect(Collectors.toList()))
                .profileImage(user.getUserDetail().getUserProfileImage())
                .introduceContent(user.getUserDetail().getIntroduceContent())
                .boardCount(user.getBoardList().size())
                .followingCount(user.getFollowList().size())
                .followerCount(user.getFromFollowList().size())
                .build();
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