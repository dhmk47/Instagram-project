package com.project.instagram.service.board;

import com.project.instagram.domain.board.*;
import com.project.instagram.domain.tag.LocationTag;
import com.project.instagram.domain.tag.LocationTagRepository;
import com.project.instagram.domain.tag.UserTag;
import com.project.instagram.domain.tag.UserTagRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.handler.exception.file.FileException;
import com.project.instagram.handler.exception.file.FileExceptionResult;
import com.project.instagram.web.dto.board.CreateBoardRequestDto;
import com.project.instagram.web.dto.board.ReadBoardResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardTypeRepository boardTypeRepository;
    private final BoardFileRepository boardFileRepository;
    private final UserTagRepository userTagRepository;
    private final LocationTagRepository locationTagRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Value("${file.path}")
    private String filePath;


    @Transactional
    @Override
    public boolean createBoard(CreateBoardRequestDto createBoardRequestDto) {
        User fromUser = entityManager.find(User.class, createBoardRequestDto.getUserCode());

        Board board = createBoardRequestDto.toBoardEntity();
        board.setBoardType(entityManager.find(BoardType.class, createBoardRequestDto.getBoardTypeCode()));
        board.setUser(fromUser);

        entityManager.persist(board);

        uploadFiles(createBoardRequestDto, board);

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
        return board;
    }

    @Override
    @Transactional
    public int loadBoardTotalCountByUserCode(Long userCode) {
        String jpql = "select count(*) from Board b where b.boardType.boardTypeCode = 1 and b.user.userCode = :userCode";
        return entityManager.createQuery(jpql, Integer.class).setParameter("userCode", userCode).getSingleResult();
    }

    private void uploadFiles(CreateBoardRequestDto createBoardRequestDto, Board board) {
        List<BoardFile> boardFileList = new ArrayList<>();
        for(MultipartFile multipartFile : createBoardRequestDto.getBoardFileList()) {
            String uuidFileName = getUUIDFileName(multipartFile.getOriginalFilename());

            uploadFilesInLocalFolder(multipartFile, uuidFileName);

            boardFileList.add(BoardFile.builder()
                    .fileName(uuidFileName)
                    .board(board)
                    .fileType(getFileType(multipartFile))
                    .build());
        }

        boardFileRepository.saveAll(boardFileList);
    }

    private void uploadFilesInLocalFolder(MultipartFile multipartFile, String fileName) {
        Path path = Paths.get(filePath + "board/images/" + fileName);

        File pathFolder = new File(filePath + "board/images");

        if(!pathFolder.exists()) {
            pathFolder.mkdirs();
        }

        try {
            Files.write(path, multipartFile.getBytes());
        } catch (IOException e) {
            try {
                Files.delete(path);
            } catch (IOException ex) {
                throw new FileException(FileExceptionResult.FILE_LOCAL_UPLOAD_EXCEPTION);
            }
            throw new FileException(FileExceptionResult.FILE_LOCAL_UPLOAD_EXCEPTION);
        }
    }

    private FileType getFileType(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        Long fileTypeCode = contentType.contains("image") ? 1L : contentType.contains("video") ? 2L : 0L;
        if(fileTypeCode == 0L) {
            throw new FileException(FileExceptionResult.FILE_CONTENT_TYPE_EXCEPTION);
        }
        return entityManager.find(FileType.class, fileTypeCode);
    }

    private String getUUIDFileName(String fileName) {
        return UUID.randomUUID().toString().replaceAll("-", "") + "_" + fileName;
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