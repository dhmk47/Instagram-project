package com.project.instagram.handler.exception.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionResult {
    ALREADY_HAS_USER_EXCEPTION(HttpStatus.BAD_REQUEST, "Already Has User Exception");

    private final HttpStatus httpStatus;
    private final String message;
}