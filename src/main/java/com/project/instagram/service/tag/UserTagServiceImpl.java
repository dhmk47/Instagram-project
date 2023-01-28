package com.project.instagram.service.tag;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.tag.UserTag;
import com.project.instagram.domain.tag.UserTagRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTagServiceImpl implements UserTagService {

    private final UserService userService;
    private final UserTagRepository userTagRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void addUserTag(Board board, List<Long> userCodeList) {
        List<User> tagUserList = userService.getUserListByUserCode(userCodeList);

        List<UserTag> userTagList = createUserTagList(tagUserList, board.getUser(), board);
        log.info("userTagList check: {}", userTagList);

        userTagRepository.saveAll(userTagList);
    }

    private List<UserTag> createUserTagList(List<User> tagUserList, User fromUser, Board board) {
        return tagUserList.stream()
                .map(toUser -> {
                    return UserTag.builder()
                            .toUser(toUser)
                            .fromUser(fromUser)
                            .board(board)
                            .build();
                })
                .collect(Collectors.toList());
    }
}