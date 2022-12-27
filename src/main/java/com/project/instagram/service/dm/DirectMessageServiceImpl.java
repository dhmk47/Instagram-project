package com.project.instagram.service.dm;

import com.project.instagram.domain.dm.DirectMessage;
import com.project.instagram.domain.dm.DirectMessageRepository;
import com.project.instagram.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectMessageServiceImpl implements DirectMessageService {

    @PersistenceContext
    private EntityManager entityManager;

    private final DirectMessageRepository directMessageRepository;

    @Override
    @Transactional
    public void sendDirectMessage() {
        User toUser = entityManager.find(User.class, 3L);
        User fromUser = entityManager.find(User.class, 1L);

        DirectMessage dm = DirectMessage.builder()
                .directMessage("안녕하세요 " + fromUser.getUserName() + "입니다.")
                .build();

        dm.setToUser(toUser);
        dm.setFromUser(fromUser);

        entityManager.persist(dm);

    }

    @Override
    @Transactional
    public List<DirectMessage> loadDirectMessage() {
        User user = entityManager.find(User.class, 2L);
        List<DirectMessage> directMessageList = directMessageRepository.findDirectMessageByToUserCode(user.getUserCode());

        log.info("dm 조회: {}", directMessageList);

        return directMessageList;
    }
}