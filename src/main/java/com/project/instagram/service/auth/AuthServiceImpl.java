package com.project.instagram.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Override
    public boolean loadFacebookLoginUser(Map<String, Object> oAuthResponseMap) {
        log.info("check: {}", oAuthResponseMap);

        try {
            oAuthResponseMap.get("user_id");
        } catch (Exception e) {
            throw new NullPointerException();
        }
        return true;
    }
}