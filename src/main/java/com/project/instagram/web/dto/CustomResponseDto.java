package com.project.instagram.web.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CustomResponseDto<T> {
    public int code;
    public String message;
    public T data;
}