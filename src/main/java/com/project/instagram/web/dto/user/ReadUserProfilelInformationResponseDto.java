package com.project.instagram.web.dto.user;

import com.project.instagram.web.dto.board.ReadBoardResponseDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReadUserProfilelInformationResponseDto {
    private String userName;
    private String userNickname;
    private String introduceContent;
    private String profileImage;
    private int boardCount;
    private int followingCount;
    private int followerCount;
    private List<ReadBoardResponseDto> boardList;
}