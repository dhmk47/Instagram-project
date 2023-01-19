package com.project.instagram.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomErrorResponseDto {
    private String errorName;
    private String errorMessage;
}