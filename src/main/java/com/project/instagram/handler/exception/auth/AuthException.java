package com.project.instagram.handler.exception.auth;

import com.project.instagram.handler.exception.SuperException;
import com.project.instagram.handler.exception.SuperExceptionResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthException extends RuntimeException implements SuperException {
    private final AuthExceptionResult authExceptionResult;

    @Override
    public SuperExceptionResult getExceptionResult() {
        return authExceptionResult;
    }
}