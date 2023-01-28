package com.project.instagram.service.tag;

import com.project.instagram.domain.board.Board;

import java.util.List;

public interface UserTagService {
    public void addUserTag(Board board, List<Long> userCodeList);
}