package com.project.instagram.domain.board;

import com.project.instagram.domain.time.BaseTimeEntity;
import com.project.instagram.domain.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class LovedBoard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lovedCode;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "board_boardCode")
    private Board board;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_userCode")
    private User user;
}
