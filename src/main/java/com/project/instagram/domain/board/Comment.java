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
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentCode;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "board_boardCode")
    private Board board;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_userCode")
    private User user;

    @NotNull
    private String comment;
    @NotNull
    private String path;
    @NotNull
    private int loved;
}
