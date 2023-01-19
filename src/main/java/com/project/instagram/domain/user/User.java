package com.project.instagram.domain.user;

import com.project.instagram.domain.alert.Alert;
import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.Comment;
import com.project.instagram.domain.board.LovedBoard;
import com.project.instagram.domain.dm.DirectMessage;
import com.project.instagram.domain.friend.BestFriend;
import com.project.instagram.domain.friend.Follow;
import com.project.instagram.domain.storage.SaveBoard;
import com.project.instagram.domain.storage.Storage;
import com.project.instagram.domain.time.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    private String userEmail;
    private String userPhoneNumber;

    @NotNull
    @OneToOne()
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
    private List<SaveBoard> saveBoardList;

    @OneToMany(mappedBy = "friendUser")
    private List<BestFriend> fromBestFriendList;

    @OneToMany(mappedBy = "user")
    private List<BestFriend> bestFriendList;
}