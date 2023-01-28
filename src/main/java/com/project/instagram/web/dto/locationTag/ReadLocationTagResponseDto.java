package com.project.instagram.web.dto.locationTag;

import lombok.Data;

@Data
public class ReadLocationTagResponseDto {
    private Long tagCode;
    private String tagName;
    private Long totalCount;

    public ReadLocationTagResponseDto(){}

    public ReadLocationTagResponseDto(Long tagCode, String tagName, Long totalCount) {
        this.tagCode = tagCode;
        this.tagName = tagName;
        this.totalCount = totalCount;
    }
}