package com.project.instagram.web.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadUserDetailResponseDto {
    private String userAddress;
    private String userProfileImage;
    private String introduceContent;
}