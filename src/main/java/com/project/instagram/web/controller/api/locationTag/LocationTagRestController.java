package com.project.instagram.web.controller.api.locationTag;

import com.project.instagram.service.tag.LocationTagService;
import com.project.instagram.web.dto.CustomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/location-tag/")
public class LocationTagRestController {

    private final LocationTagService locationTagService;

    @GetMapping("/information/{locationTagName}")
    public ResponseEntity<?> getLocationTagInformation(@PathVariable String locationTagName) {
        return ResponseEntity.ok(new CustomResponseDto<>(1, "LocationTag Information Retrieval Successful", locationTagService.getLocationTagInformation(locationTagName)));
    }
}