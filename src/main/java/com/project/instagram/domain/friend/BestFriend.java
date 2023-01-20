package com.project.instagram.domain.friend;

import com.project.instagram.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BestFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bestFriendCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userCode")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friendUserCode")
    private User friendUser;
}