package com.project.instagram.service.tag;

import com.project.instagram.domain.board.Board;
import com.project.instagram.web.dto.locationTag.ReadLocationTagResponseDto;

import java.util.List;

public interface LocationTagService {
    public void addLocationTag(Board board, List<String> locationTagNameList);
    public List<ReadLocationTagResponseDto> getLocationTagInformation(String searchTagName);
}