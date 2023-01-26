package com.project.instagram.service.board;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.BoardRepository;
import com.project.instagram.domain.board.BoardType;
import com.project.instagram.domain.board.BoardTypeRepository;
import com.project.instagram.domain.tag.LocationTag;
import com.project.instagram.domain.tag.LocationTagRepository;
import com.project.instagram.domain.tag.UserTag;
import com.project.instagram.domain.tag.UserTagRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.web.dto.board.CreateBoardRequestDto;
import com.project.instagram.web.dto.board.ReadBoardResponseDto;
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
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardTypeRepository boardTypeRepository;
    private final UserTagRepository userTagRepository;
    private final LocationTagRepository locationTagRepository;

    @PersistenceContext
    private final EntityManager entityManager;


    @Transactional
    @Override
    public boolean createBoard(CreateBoardRequestDto createBoardRequestDto) {
        User fromUser = entityManager.find(User.class, createBoardRequestDto.getUserCode());

        Board board = createBoardRequestDto.toBoardEntity();
        board.setBoardType(entityManager.find(BoardType.class, createBoardRequestDto.getBoardTypeCode()));
        board.setUser(fromUser);

        entityManager.persist(board);

        List<User> tagUserList = findUserListByUserNickname(createBoardRequestDto);

        List<UserTag> userTagList = createUserTagList(tagUserList, fromUser, board);

        List<LocationTag> locationTagList = createLocationTagList(createBoardRequestDto, board);

        log.info("userTagList check: {}", userTagList);
        log.info("locationTagList check: {}", locationTagList);

        userTagRepository.saveAll(userTagList);
        locationTagRepository.saveAll(locationTagList);

        return true;
    }

    @Transactional
    @Override
    public Board loadBoardByBoardCode(Long boardCode) {
        Board board = entityManager.find(Board.class, boardCode);

        log.info("board: {}", board.getBoardType());
        log.info("board: {}", board.getStorage());
        log.info("board: {}", board);

        return board;
    }

    @Override
    @Transactional
    public int loadBoardTotalCountByUserCode(Long userCode) {
        String jpql = "select count(*) from Board b where b.boardType.boardTypeCode = 1 and b.user.userCode = :userCode";
        return entityManager.createQuery(jpql, Integer.class).setParameter("userCode", userCode).getSingleResult();
    }

    private String createJpqlSelectInUserNickname(CreateBoardRequestDto createBoardRequestDto) {
        String jpql = "select u from User u where u.userNickname in (";
        int index = 0;

        for(String userNickname : createBoardRequestDto.getUserTagList()) {
            jpql += userNickname + (index != createBoardRequestDto.getUserTagList().size() - 1 ? ", " : ")");
            index++;
        }
        log.info("jpql check: {}", jpql);
        return jpql;
    }

    private List<User> findUserListByUserNickname(CreateBoardRequestDto createBoardRequestDto) {
        List<User> tagUserList = Collections.emptyList();

        if(createBoardRequestDto.getUserTagList().size() > 0) {
            String jpql = createJpqlSelectInUserNickname(createBoardRequestDto);
            tagUserList = entityManager.createQuery(jpql, User.class).getResultList();

        }

        return tagUserList;
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

    private List<LocationTag> createLocationTagList(CreateBoardRequestDto createBoardRequestDto, Board board) {
        return createBoardRequestDto.getLocationTagList().stream()
                .map(tagName -> {
                    return LocationTag.builder()
                            .board(board)
                            .tagName(tagName)
                            .build();
                })
                .collect(Collectors.toList());
    }
}