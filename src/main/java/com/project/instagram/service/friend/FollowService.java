package com.project.instagram.service.friend;

import com.project.instagram.web.dto.friend.ReadFollowListResponseDto;

import java.util.List;

public interface FollowService {
    public boolean addFollow();
    public List<ReadFollowListResponseDto> loadFollowUser();
}