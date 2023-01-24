package com.project.instagram.handler.exception;

import org.springframework.http.HttpStatus;

public interface SuperExceptionResult {
    public HttpStatus getStatus();
    public String getName();
    public String getMessage();
}