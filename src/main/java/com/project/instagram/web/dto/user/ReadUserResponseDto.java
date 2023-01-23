package com.project.instagram.web.dto.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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
    private String userRole;

    private ReadUserDetailResponseDto userDetail;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

//    private List<ReadBoardResponseDto> boardList;
//
//    private List<Comment> commentList;
//
//    private List<LovedBoard> lovedBoardList;
//
//    private List<ReadFollowResponseDto> followList;
//    private List<ReadFollowResponseDto> fromFollowList;
//
//    private List<DirectMessage> receivedDirectMessageList;
//    private List<DirectMessage> sendDirectMessageList;
//
//    private List<Alert> receivedAlertList;
//    private List<Alert> fromAlertList;
//
//    private List<Storage> storageList;
//
//    private List<SaveBoard> saveBoardList;
//
//    private List<BestFriend> fromBestFriendList;
//    private List<BestFriend> bestFriendList;
}