package com.project.instagram.domain.board;

import com.project.instagram.domain.time.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class BoardType extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardTypeCode;

    @NotNull
    private String boardTypeName;

    @OneToMany(mappedBy = "boardType")
    private List<Board> boardList;
}
