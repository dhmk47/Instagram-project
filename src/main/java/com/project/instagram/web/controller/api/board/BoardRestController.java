package com.project.instagram.web.controller.api.board;

import com.project.instagram.handler.aop.annotation.Log;
import com.project.instagram.service.board.BoardService;
import com.project.instagram.web.dto.CustomResponseDto;
import com.project.instagram.web.dto.board.CreateBoardRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@Slf4j
public class BoardRestController {

    private final BoardService boardService;

    @Log
    @PostMapping("")
    public ResponseEntity<?> createNewBoard(@Valid CreateBoardRequestDto createBoardRequestDto) {
        return ResponseEntity.ok(new CustomResponseDto<>(1, "Posting Successful", boardService.createBoard(createBoardRequestDto)));
    }

    @GetMapping("/count/user/{userCode}")
    public ResponseEntity<?> getBoardTotalCountByUserCode(@PathVariable Long userCode) {
        return ResponseEntity.ok(new CustomResponseDto<>(1, "Successful Loading of Number of Board", boardService.loadBoardTotalCountByUserCode(userCode)));
    }
}