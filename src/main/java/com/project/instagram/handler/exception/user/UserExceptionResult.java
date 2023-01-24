package com.project.instagram.handler.exception.user;

import com.project.instagram.handler.exception.SuperExceptionResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionResult implements SuperExceptionResult {
    NO_RESULT_USER_BY_USER_NICKNAME(HttpStatus.BAD_REQUEST, "No User Search Results");

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