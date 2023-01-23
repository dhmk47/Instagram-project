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
@Data
@EqualsAndHashCode(callSuper = false)
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

    public ReadBoardFileResponseDto toBoardFileDto() {
        return ReadBoardFileResponseDto.builder()
                .fileCode(fileCode)
                .fileName(fileName)
                .build();
    }
}