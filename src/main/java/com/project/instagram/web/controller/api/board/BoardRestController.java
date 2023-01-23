package com.project.instagram.web.controller.api.board;

import com.project.instagram.service.board.BoardService;
import com.project.instagram.web.dto.CustomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@Slf4j
public class BoardRestController {

    private final BoardService boardService;

    @GetMapping("/count/user/{userCode}")
    public ResponseEntity<?> getBoardTotalCountByUserCode(@PathVariable Long userCode) {
        return ResponseEntity.ok(new CustomResponseDto<>(1, "Successful Loading of Number of Board", boardService.loadBoardTotalCountByUserCode(userCode)));
    }
}