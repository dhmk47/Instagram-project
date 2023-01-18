package com.project.instagram.service.auth;

import java.util.Map;

public interface AuthService {
    public boolean loadFacebookLoginUser(Map<String, Object> oAuthResponseMap);
}
