package com.project.instagram.service.auth;

import com.project.instagram.web.dto.user.CreateUserRequestDto;

import java.util.Map;

public interface AuthService {
    public boolean signUpUser(CreateUserRequestDto createUserRequestDto);
    public boolean loadFacebookLoginUser(Map<String, Object> oAuthResponseMap);
}
