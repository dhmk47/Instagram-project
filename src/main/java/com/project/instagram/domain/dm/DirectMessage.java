package com.project.instagram.domain.dm;

import com.project.instagram.domain.time.BaseTimeEntity;
import com.project.instagram.domain.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
//@EqualsAndHashCode(callSuper = false)
public class DirectMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long directMessageCode;

    @ManyToOne
    @JoinColumn(name = "toUserCode")
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "fromUserCode")
    private User fromUser;

    @NotNull
    private String directMessage;

}