package com.project.instagram.domain.friend;

import com.project.instagram.domain.time.BaseTimeEntity;
import com.project.instagram.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class Follow extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followCode;

    @ManyToOne
    @JoinColumn(name = "toUserCode")
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "fromUserCode")
    private User fromUser;
}