package com.project.instagram.web.dto.user;

import com.project.instagram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequestDto {
    private String userId;
    private String userPassword;
    private String userName;
    private String userNickname;
    private String userPhoneNumber;
    private String userEmail;

    private boolean userPhoneNumberFlag;
    private boolean userEmailFlag;

    public User toUserEntity(String encodeUserPassword) {
        return User.builder()
                .userId(userId)
                .userPassword(encodeUserPassword)
                .userName(userName)
                .userNickname(userNickname)
                .userPhoneNumber(userPhoneNumber)
                .userEmail(userEmail)
                .build();
    }
}