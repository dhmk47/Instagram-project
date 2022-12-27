package com.project.instagram.domain.alert;

import com.project.instagram.domain.board.Comment;
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
public class Alert extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertCode;

    @ManyToOne
    @JoinColumn(name = "toUserCode")
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "alertTypeCode")
    private AlertType alertType;

    @ManyToOne
    @JoinColumn(name = "fromUserCode")
    private User fromUser;

    @OneToOne
    @JoinColumn(name = "commentCode")
    private Comment comment;
}