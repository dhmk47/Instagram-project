package com.project.instagram.handler.exception.file;

import com.project.instagram.handler.exception.SuperException;
import com.project.instagram.handler.exception.SuperExceptionResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileException extends RuntimeException implements SuperException {
    private final FileExceptionResult fileExceptionResult;

    @Override
    public SuperExceptionResult getExceptionResult() {
        return fileExceptionResult;
    }
}