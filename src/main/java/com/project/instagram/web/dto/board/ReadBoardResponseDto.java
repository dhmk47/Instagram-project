package com.project.instagram.web.dto.board;

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