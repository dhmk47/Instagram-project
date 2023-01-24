package com.project.instagram.handler.exception.user;

import com.project.instagram.handler.exception.SuperException;
import com.project.instagram.handler.exception.SuperExceptionResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException implements SuperException {
    private final UserExceptionResult userExceptionResult;

    @Override
    public SuperExceptionResult getExceptionResult() {
        return userExceptionResult;
    }
}