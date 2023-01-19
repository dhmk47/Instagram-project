package com.project.instagram.handler.exception.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthException extends RuntimeException {
    private final AuthExceptionResult authExceptionResult;
}