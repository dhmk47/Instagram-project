package com.project.instagram.service.user;

import com.project.instagram.web.dto.user.ReadUserProfileInformationResponseDto;
import com.project.instagram.web.dto.user.ReadUserResponseDto;

import java.util.List;

public interface UserService {
    public List<ReadUserResponseDto> searchUserList(String searchValue);
    public ReadUserProfileInformationResponseDto getUserDetailCountInformation(String userNickname);
    public void addFollow();
    public void loadFollowUser();
}
