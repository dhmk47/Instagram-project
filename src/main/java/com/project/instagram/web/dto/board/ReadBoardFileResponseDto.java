package com.project.instagram.web.dto.board;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadBoardFileResponseDto {
    private Long fileCode;
    private String fileName;
}