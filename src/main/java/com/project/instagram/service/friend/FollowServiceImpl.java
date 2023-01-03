package com.project.instagram.service.friend;

import com.project.instagram.domain.friend.Follow;
import com.project.instagram.domain.friend.FollowRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.web.dto.friend.ReadFollowListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    @PersistenceContext
    private EntityManager entityManager;

    private final FollowRepository followRepository;

    @Override
    @Transactional
    public boolean addFollow() {
        User toUser = entityManager.find(User.class, 2L);
        User fromUser = entityManager.find(User.class, 1L);

        Follow follow = Follow.builder()
                .build();

        follow.setFromUser(fromUser);
        follow.setToUser(toUser);

        entityManager.persist(follow);

        return false;
    }

    @Transactional
    @Override
    public List<ReadFollowListResponseDto> loadFollowUser() {
        Query query = entityManager.createNamedQuery("findFollowingListByUserCode").setParameter("toUserCode", 1);

        List<ReadFollowListResponseDto> followList = query.getResultList();
        followList.forEach(follow -> log.info("check: {}", follow));
        return followList;
    }

}