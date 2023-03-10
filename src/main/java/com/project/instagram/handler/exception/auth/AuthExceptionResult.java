package com.project.instagram.handler.exception.auth;

import com.project.instagram.handler.exception.SuperExceptionResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionResult implements SuperExceptionResult {
    ALREADY_HAS_USER_EXCEPTION(HttpStatus.BAD_REQUEST, "Already Has User Exception");

    private final HttpStatus httpStatus;
    private final String message;


    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getName() {
        return this.name();
    }
}