package com.project.instagram.web.controller.api.user;

import com.project.instagram.handler.aop.annotation.Log;
import com.project.instagram.service.user.UserService;
import com.project.instagram.web.dto.CustomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class UserRestController {

    private final UserService userService;

    @Log
    @GetMapping("/list/search")
    public ResponseEntity<?> searchUserListByKeyValue(@RequestParam String searchValue) {
        return ResponseEntity.ok(new CustomResponseDto<>(1, "User List Search Successful", userService.searchUserList(searchValue)));
    }

    @Log
    @GetMapping("/profile/detail")
    public ResponseEntity<?> getUserDetailCountInformationByUserNickname(@RequestParam String userNickname) {
        return ResponseEntity.ok(new CustomResponseDto<>(1, "Retrieve User Detail Count Successfully", userService.getUserDetailCountInformation(userNickname)));
    }
}