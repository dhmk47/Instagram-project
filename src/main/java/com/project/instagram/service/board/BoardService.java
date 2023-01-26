package com.project.instagram.service.board;

import com.project.instagram.domain.board.Board;
import com.project.instagram.web.dto.board.CreateBoardRequestDto;
import com.project.instagram.web.dto.board.ReadBoardResponseDto;

public interface BoardService {
    public boolean createBoard(CreateBoardRequestDto createBoardRequestDto);
    public Board loadBoardByBoardCode(Long boardCode);
    public int loadBoardTotalCountByUserCode(Long boardCode);
}