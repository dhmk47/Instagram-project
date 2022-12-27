package com.project.instagram.service.friend;

import com.project.instagram.domain.friend.BestFriend;
import com.project.instagram.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Service
@RequiredArgsConstructor
public class BestFriendServiceImpl implements BestFriendService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void addBestFriend(Long userCode, Long friendUserCode) {
        User user = entityManager.find(User.class, userCode);
        User friend = entityManager.find(User.class, friendUserCode);

        BestFriend bestFriend = BestFriend.builder()
                .user(user)
                .friendUser(friend)
                .build();

        entityManager.persist(bestFriend);
    }
}