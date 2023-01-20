package com.project.instagram.service.user;

import com.project.instagram.web.dto.user.ReadUserResponseDto;

import java.util.List;

public interface UserService {
    public List<ReadUserResponseDto> searchUserList(String searchValue);

    public void addFollow();
    public void loadFollowUser();
}
