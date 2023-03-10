package com.project.instagram.service.user;

import com.project.instagram.domain.friend.Follow;
import com.project.instagram.domain.friend.FollowRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserRepository;
import com.project.instagram.handler.exception.user.UserException;
import com.project.instagram.handler.exception.user.UserExceptionResult;
import com.project.instagram.web.dto.user.ReadUserResponseDto;
import com.project.instagram.web.dto.user.ReadUserProfileInformationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
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
    public ReadUserProfileInformationResponseDto getUserDetailCountInformation(String userNickname, User loginUser) {
        String jpql = null;
        if(loginUser.getUserNickname().equals(userNickname)) {
            jpql = "select distinct u from User u left join fetch u.boardList left join u.followList left join u.fromFollowList left join u.savedBoardList left join u.taggedList where u.userNickname = :userNickname";

        }else {
            jpql = "select distinct u from User u left join fetch u.boardList left join u.followList left join u.fromFollowList where u.userNickname = :userNickname";

        }
        List<User> userList = entityManager.createQuery(jpql, User.class).setParameter("userNickname", userNickname).getResultList();

        if(userList.isEmpty()) {
            throw new UserException(UserExceptionResult.NO_RESULT_USER_BY_USER_NICKNAME);
        }

        return userList.get(0).toUserProfileInformationDto();
    }

    @Override
    @Transactional
    public List<User> getUserListByUserCode(List<Long> userCodeList) {
        List<User> tagUserList = Collections.emptyList();

        if(userCodeList.size() > 0) {
            String jpql = createJpqlSelectInUserCode(userCodeList);
            tagUserList = entityManager.createQuery(jpql, User.class).getResultList();

        }

        return tagUserList;
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

    private String createJpqlSelectInUserCode(List<Long> userCodeList) {
        String jpql = "select u from User u where u.userCode in (";
        int index = 0;

        for(Long userCode : userCodeList) {
            jpql += userCode + (index != userCodeList.size() - 1 ? ", " : ")");
            index++;
        }
        log.info("jpql check: {}", jpql);
        return jpql;
    }
}