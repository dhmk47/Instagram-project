package com.project.instagram.domain.board;

import com.project.instagram.domain.time.BaseTimeEntity;
import com.project.instagram.web.dto.board.ReadBoardFileResponseDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BoardFile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileCode;

    @NotNull
    private String fileName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardCode")
    private Board board;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileTypeCode")
    private FileType fileType;

    public ReadBoardFileResponseDto toBoardFileDto() {
        return ReadBoardFileResponseDto.builder()
                .fileCode(fileCode)
                .fileName(fileName)
                .fileTypeCode(fileType.getFileTypeCode())
                .build();
    }
}