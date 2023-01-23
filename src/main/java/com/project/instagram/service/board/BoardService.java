package com.project.instagram.service.board;

import com.project.instagram.domain.board.Board;

public interface BoardService {
    public Long createBoard(Board board, Long boardTypeCode);
    public Board loadBoardByBoardCode(Long boardCode);
    public int loadBoardTotalCountByUserCode(Long boardCode);
}