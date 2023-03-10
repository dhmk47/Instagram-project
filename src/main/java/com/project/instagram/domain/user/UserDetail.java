package com.project.instagram.domain.user;

import com.project.instagram.domain.time.BaseTimeEntity;
import com.project.instagram.web.dto.user.ReadUserDetailResponseDto;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_dtl")
public class UserDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCode;
    private String userAddress;
    private String userProfileImage;
    private String introduceContent;

    public ReadUserDetailResponseDto toUserDetailDto() {
        return ReadUserDetailResponseDto.builder()
                .userAddress(userAddress)
                .userProfileImage(userProfileImage)
                .introduceContent(introduceContent)
                .build();
    }
}
