package com.project.instagram.web.controller.api;

import com.project.instagram.service.auth.AuthService;
import com.project.instagram.web.dto.CustomResponseDto;
import com.project.instagram.web.dto.user.CreateUserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("sign-up")
    public ResponseEntity<?> signUpUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        return ResponseEntity.ok(new CustomResponseDto<>(1, "Successful Membership", authService.signUpUser(createUserRequestDto)));
    }

    @PostMapping("/oauth/facebook")
    public ResponseEntity<?> loginFacebookUser(@RequestBody Map<String, Object> oAuthResponseMap) {
        boolean status = false;

        try {
            status = authService.loadFacebookLoginUser(oAuthResponseMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new CustomResponseDto<>(-1, "Facebook user login failed", status));
        }

        return ResponseEntity.ok(new CustomResponseDto<>(1, "Facebook user login successful", status));
    }


}