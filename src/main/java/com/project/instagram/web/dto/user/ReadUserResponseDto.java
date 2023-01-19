package com.project.instagram.web.dto.user;

import com.project.instagram.domain.alert.Alert;
import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.Comment;
import com.project.instagram.domain.board.LovedBoard;
import com.project.instagram.domain.dm.DirectMessage;
import com.project.instagram.domain.friend.BestFriend;
import com.project.instagram.domain.friend.Follow;
import com.project.instagram.domain.storage.SaveBoard;
import com.project.instagram.domain.storage.Storage;
import com.project.instagram.domain.user.UserDetail;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReadUserResponseDto {

    private Long userCode;
    private String userId;
    private String userName;
    private String userPassword;
    private String userNickname;
    private String userEmail;
    private String userPhoneNumber;

    private UserDetail userDetail;

    private List<Board> boardList;

    private List<Comment> commentList;

    private List<LovedBoard> lovedBoardList;

    private List<Follow> followList;
    private List<Follow> fromFollowList;

    private List<DirectMessage> receivedDirectMessageList;
    private List<DirectMessage> sendDirectMessageList;

    private List<Alert> receivedAlertList;
    private List<Alert> fromAlertList;

    private List<Storage> storageList;

    private List<SaveBoard> saveBoardList;

    private List<BestFriend> fromBestFriendList;
    private List<BestFriend> bestFriendList;
}