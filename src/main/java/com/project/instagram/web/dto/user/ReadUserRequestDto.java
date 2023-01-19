package com.project.instagram.web.dto.user;

import com.project.instagram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReadUserRequestDto {
    private String userId;
    private String userPassword;

    public User toUserEntity() {
        return User.builder()
                .userId(userId)
                .userPassword(userPassword)
                .build();
    }
}