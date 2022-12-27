package com.project.instagram.service.board;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.BoardType;

import java.util.List;

public interface BoardTypeService {
    public boolean createBoardType(BoardType boardType);
    public List<Board> loadAllBoardByBoardType(Long boardTypeCode);
}