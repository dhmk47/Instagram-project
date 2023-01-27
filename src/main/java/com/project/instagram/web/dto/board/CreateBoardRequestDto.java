package com.project.instagram.web.dto.board;

import com.project.instagram.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateBoardRequestDto {
    private String content;

    @NotNull
    private boolean hideViewAndLikeCountFlag;

    @NotNull
    private boolean disableCommentFlag;

    @NotNull
    private Long userCode;

    @NotNull
    private Long boardTypeCode;
    @NotNull
    private List<MultipartFile> boardFileList;
    @NotNull
    private List<String> userTagList;
    @NotNull
    private List<String> locationTagList;

    public Board toBoardEntity() {
        return Board.builder()
                .content(content)
                .hideViewAndLikeCountFlag(hideViewAndLikeCountFlag ? 1 : 0)
                .disableCommentFlag(disableCommentFlag ? 1 : 0)
                .build();
    }
}