package com.project.instagram.domain.board;

import com.project.instagram.domain.time.BaseTimeEntity;
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
    @ManyToOne
    @JoinColumn(name = "board_boardCode")
    private Board board;
}
