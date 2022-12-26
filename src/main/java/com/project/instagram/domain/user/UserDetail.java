package com.project.instagram.domain.user;

import com.project.instagram.domain.time.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Table(name = "user_dtl")
public class UserDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCode;

    private String userAddress;
    private String userPhoneNumber;
}
