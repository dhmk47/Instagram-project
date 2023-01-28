package com.project.instagram.domain.board;

import com.project.instagram.domain.storage.SavedBoard;
import com.project.instagram.domain.storage.Storage;
import com.project.instagram.domain.tag.LocationTag;
import com.project.instagram.domain.tag.UserTag;
import com.project.instagram.domain.time.BaseTimeEntity;
import com.project.instagram.domain.user.User;
import com.project.instagram.web.dto.board.ReadBoardResponseDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardCode;

    private String content;
    @NotNull
    private int hideViewAndLikeCountFlag;
    @NotNull
    private int disableCommentFlag;

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
    private List<SavedBoard> saveBoardList;

    @OneToMany(mappedBy = "board")
    private List<UserTag> userTagList;

    @OneToMany(mappedBy = "board")
    private List<LocationTag> locationTagList;

    public ReadBoardResponseDto toBoardDto() {
        return ReadBoardResponseDto.builder()
                .boardCode(boardCode)
                .content(content)
                .userNickname(user.getUserNickname())
                .boardTypeCode(boardType.getBoardTypeCode())
                .boardFileList(boardFileList == null ? Collections.emptyList() : boardFileList.stream()
                        .map(BoardFile::toBoardFileDto)
                        .collect(Collectors.toList()))
                .build();
    }
}