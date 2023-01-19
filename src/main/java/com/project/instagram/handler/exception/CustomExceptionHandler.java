package com.project.instagram.handler.exception;

import com.project.instagram.handler.exception.auth.AuthException;
import com.project.instagram.handler.exception.auth.AuthExceptionResult;
import com.project.instagram.web.dto.CustomErrorResponseDto;
import com.project.instagram.web.dto.CustomResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> catchAllException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new CustomResponseDto<>(-1, "Server Error", false));
    }
    @ExceptionHandler({AuthException.class})
    public ResponseEntity<?> alreadyHasUserException(AuthException authException) {
        return makeErrorResponseEntity(authException.getAuthExceptionResult());
    }

    private ResponseEntity<?> makeErrorResponseEntity(AuthExceptionResult authExceptionResult) {
        return ResponseEntity.status(authExceptionResult.getHttpStatus())
                .body(new CustomErrorResponseDto(authExceptionResult.name(), authExceptionResult.getMessage()));
    }
}