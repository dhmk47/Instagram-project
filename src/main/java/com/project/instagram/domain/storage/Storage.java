package com.project.instagram.domain.storage;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.time.BaseTimeEntity;
import com.project.instagram.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Storage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storageBoardCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userCode")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardCode")
    private Board board;
}