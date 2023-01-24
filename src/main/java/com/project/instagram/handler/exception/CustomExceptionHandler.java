package com.project.instagram.handler.exception;

import com.project.instagram.handler.exception.auth.AuthException;
import com.project.instagram.handler.exception.auth.AuthExceptionResult;
import com.project.instagram.handler.exception.user.UserException;
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
        log.error(">>>>>>>>>>>>>>>>>>>>> Error!!!");
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new CustomResponseDto<>(-1, "Server Error", false));
    }

    @ExceptionHandler({AuthException.class, UserException.class})
    public ResponseEntity<?> alreadyHasUserException(SuperException exception) {
        return makeErrorResponseEntity(exception.getExceptionResult());
    }

    private ResponseEntity<?> makeErrorResponseEntity(SuperExceptionResult exceptionResult) {
        log.info(">>>>>>>>>>>>>>>><<<<<<<<<<<<< {}, {}, {}", exceptionResult.getStatus(), exceptionResult.getName(), exceptionResult.getMessage());
        return ResponseEntity.status(exceptionResult.getStatus())
                .body(new CustomErrorResponseDto(exceptionResult.getName(), exceptionResult.getMessage()));
    }
}