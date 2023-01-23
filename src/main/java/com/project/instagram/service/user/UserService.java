package com.project.instagram.service.user;

import com.project.instagram.web.dto.user.ReadUserProfilelInformationResponseDto;
import com.project.instagram.web.dto.user.ReadUserResponseDto;

import java.util.List;

public interface UserService {
    public List<ReadUserResponseDto> searchUserList(String searchValue);
    public ReadUserProfilelInformationResponseDto getUserDetailCountInformation(String userNickname);
    public void addFollow();
    public void loadFollowUser();
}
