package com.project.instagram.web.controller.api.locationTag;

import com.project.instagram.service.tag.LocationTagService;
import com.project.instagram.web.dto.CustomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/location-tag")
public class LocationTagRestController {

    private final LocationTagService locationTagService;

    @GetMapping("/information/list")
    public ResponseEntity<?> getLocationTagInformation(@RequestParam String searchValue) {
        return ResponseEntity.ok(new CustomResponseDto<>(1, "LocationTag Information Retrieval Successful", locationTagService.getLocationTagInformation(searchValue)));
    }
}