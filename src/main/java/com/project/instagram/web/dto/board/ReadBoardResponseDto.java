package com.project.instagram.web.dto.board;

import com.project.instagram.domain.board.BoardFile;
import com.project.instagram.domain.board.BoardType;
import com.project.instagram.domain.board.Comment;
import com.project.instagram.domain.board.LovedBoard;
import com.project.instagram.domain.storage.SaveBoard;
import com.project.instagram.domain.storage.Storage;
import com.project.instagram.domain.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReadBoardResponseDto {
    private Long boardCode;
    private String content;
    private String userNickname;
    private Long boardTypeCode;
    private List<ReadBoardFileResponseDto> boardFileList;
//    private List<LovedBoard> lovedBoardList;
//    private List<Comment> commentList;
//    private Storage storage;
//    private List<SaveBoard> saveBoardList;
}