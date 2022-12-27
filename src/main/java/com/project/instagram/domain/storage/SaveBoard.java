package com.project.instagram.domain.storage;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.time.BaseTimeEntity;
import com.project.instagram.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SaveBoard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saveBoardCode;

    @ManyToOne
    @JoinColumn(name = "userCode")
    private User user;

    @ManyToOne
    @JoinColumn(name = "boardCode")
    private Board board;
}