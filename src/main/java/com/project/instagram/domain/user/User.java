package com.project.instagram.domain.user;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.Comment;
import com.project.instagram.domain.board.LovedBoard;
import com.project.instagram.domain.time.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "user_mst")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCode;

    @Column(unique = true)
    private String userId;

    @NotNull
    private String userName;
    @NotNull
    private String userPassword;
    @NotNull
    private String userNickname;
    @NotNull
    private String userEmail;

    @NotNull
    @OneToOne()
    @JoinColumn(name = "userDetail_userCode")
    private UserDetail userDetail;

    @OneToMany(mappedBy = "user")
    private List<Board> boardList;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user")
    private List<LovedBoard> lovedBoardList;
}