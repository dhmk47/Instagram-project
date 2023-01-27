package com.project.instagram.handler.exception.file;

import com.project.instagram.handler.exception.SuperExceptionResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FileExceptionResult implements SuperExceptionResult {

    FILE_LOCAL_UPLOAD_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "File Local Upload Error"),
    FILE_CONTENT_TYPE_EXCEPTION(HttpStatus.BAD_REQUEST, "File Content Types Not Allowed");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return this.httpStatus;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
