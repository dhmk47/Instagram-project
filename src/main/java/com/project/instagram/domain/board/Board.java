package com.project.instagram.domain.board;

import com.project.instagram.domain.storage.SaveBoard;
import com.project.instagram.domain.storage.Storage;
import com.project.instagram.domain.time.BaseTimeEntity;
import com.project.instagram.domain.user.User;
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
//@ToString
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardCode;

    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userCode")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardTypeCode")
    private BoardType boardType;

    @OneToMany(mappedBy = "board")
    private List<BoardFile> boardFileList;

    @OneToMany(mappedBy = "board")
    private List<LovedBoard> lovedBoardList;

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList;

    @OneToOne(mappedBy = "board", fetch = FetchType.LAZY)
    private Storage storage;

    @OneToMany(mappedBy = "board")
    private List<SaveBoard> saveBoardList;
}