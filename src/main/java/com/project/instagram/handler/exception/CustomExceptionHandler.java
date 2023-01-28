package com.project.instagram.handler.exception;

import com.project.instagram.handler.exception.auth.AuthException;
import com.project.instagram.handler.exception.auth.AuthExceptionResult;
import com.project.instagram.handler.exception.file.FileException;
import com.project.instagram.handler.exception.user.UserException;
import com.project.instagram.web.dto.CustomErrorResponseDto;
import com.project.instagram.web.dto.CustomResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errorList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new CustomErrorResponseDto(HttpStatus.BAD_REQUEST.toString(), errorList.toString()));
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new CustomErrorResponseDto(HttpStatus.BAD_REQUEST.toString(), errorList.toString()));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> catchAllException(Exception e) {
        log.error(">>>>>>>>>>>>>>>>>>>>> Error!!!");
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new CustomResponseDto<>(-1, "Server Error", false));
    }

    @ExceptionHandler({AuthException.class, UserException.class, FileException.class})
    public ResponseEntity<?> alreadyHasUserException(SuperException exception) {
        return makeErrorResponseEntity(exception.getExceptionResult());
    }

    private ResponseEntity<?> makeErrorResponseEntity(SuperExceptionResult exceptionResult) {
        log.info(">>>>>>>>>>>>>>>><<<<<<<<<<<<< {}, {}, {}", exceptionResult.getStatus(), exceptionResult.getName(), exceptionResult.getMessage());
        return ResponseEntity.status(exceptionResult.getStatus())
                .body(new CustomErrorResponseDto(exceptionResult.getName(), exceptionResult.getMessage()));
    }
}