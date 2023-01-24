package com.project.instagram.domain.user;

import com.project.instagram.domain.alert.Alert;
import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.Comment;
import com.project.instagram.domain.board.LovedBoard;
import com.project.instagram.domain.dm.DirectMessage;
import com.project.instagram.domain.friend.BestFriend;
import com.project.instagram.domain.friend.Follow;
import com.project.instagram.domain.storage.SavedBoard;
import com.project.instagram.domain.storage.Storage;
import com.project.instagram.domain.tag.Tag;
import com.project.instagram.domain.time.BaseTimeEntity;
import com.project.instagram.web.dto.user.ReadUserProfileInformationResponseDto;
import com.project.instagram.web.dto.user.ReadUserResponseDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "user_mst")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCode;

    @NotNull
    @Column(unique = true)
    private String userId;

    @NotNull
    private String userName;
    @NotNull
    private String userPassword;
    @NotNull
    private String userNickname;

    @NotNull
    @Builder.Default
    private String userRole = "ROLE_USER";

    private String userEmail;
    private String userPhoneNumber;


    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detailCode")
    private UserDetail userDetail;

    @OneToMany(mappedBy = "user")
    private List<Board> boardList;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user")
    private List<LovedBoard> lovedBoardList;

    @OneToMany(mappedBy = "fromUser")
    private List<Follow> followList;

    @OneToMany(mappedBy = "toUser")
    private List<Follow> fromFollowList;

    @OneToMany(mappedBy = "toUser")
    private List<DirectMessage> receivedDirectMessageList;

    @OneToMany(mappedBy = "fromUser")
    private List<DirectMessage> sendDirectMessageList;

    @OneToMany(mappedBy = "toUser")
    private List<Alert> receivedAlertList;

    @OneToMany(mappedBy = "fromUser")
    private List<Alert> fromAlertList;

    @OneToMany(mappedBy = "user")
    private List<Storage> storageList;

    @OneToMany(mappedBy = "user")
    private List<SavedBoard> savedBoardList;

    @OneToMany(mappedBy = "friendUser")
    private List<BestFriend> fromBestFriendList;

    @OneToMany(mappedBy = "user")
    private List<BestFriend> bestFriendList;

    @OneToMany(mappedBy = "fromUser")
    private List<Tag> tagList;

    @OneToMany(mappedBy = "toUser")
    private List<Tag> taggedList;

    public List<String> getUserRoleList() {
        if(userRole.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.asList(userRole.replaceAll(" ", "").split(","));
    }

    public ReadUserResponseDto toUserDto() {
        return ReadUserResponseDto.builder()
                .userCode(userCode)
                .userId(userId)
                .userName(userName)
                .userPassword(userPassword)
                .userNickname(userNickname)
                .userEmail(userEmail)
                .userPhoneNumber(userPhoneNumber)
                .userRole(userRole)
                .userDetail(userDetail.toUserDetailDto())
//                .boardList(boardList)
//                .commentList(commentList)
//                .lovedBoardList(lovedBoardList)
//                .followList(followList)
//                .fromFollowList(fromFollowList)
//                .receivedDirectMessageList(receivedDirectMessageList)
//                .sendDirectMessageList(sendDirectMessageList)
//                .receivedAlertList(receivedAlertList)
//                .fromAlertList(fromAlertList)
//                .storageList(storageList)
//                .saveBoardList(saveBoardList)
//                .fromBestFriendList(fromBestFriendList)
//                .bestFriendList(bestFriendList)
                .build();
    }

    public ReadUserProfileInformationResponseDto toUserProfileInformationDto() {
        return ReadUserProfileInformationResponseDto.builder()
                .userCode(userCode)
                .userName(userName)
                .userNickname(userNickname)
                .boardList(boardList.stream()
                        .map(Board::toBoardDto)
                        .collect(Collectors.toList()))
                .savedBoardList(savedBoardList.stream()
                        .map(savedBoard -> savedBoard.getBoard().toBoardDto())
                        .collect(Collectors.toList()))
                .taggedBoardList(taggedList.stream()
                        .map(taggedBoard -> taggedBoard.getBoard().toBoardDto())
                        .collect(Collectors.toList()))
                .profileImage(userDetail.getUserProfileImage())
                .introduceContent(userDetail.getIntroduceContent() == null ? "" : userDetail.getIntroduceContent())
                .boardCount(boardList.size())
                .followingCount(followList.size())
                .followerCount(fromFollowList.size())
                .build();
    }
}