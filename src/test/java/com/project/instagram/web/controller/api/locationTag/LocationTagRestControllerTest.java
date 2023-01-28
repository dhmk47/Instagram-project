package com.project.instagram.web.controller.api.locationTag;

import com.google.gson.Gson;
import com.project.instagram.handler.exception.CustomExceptionHandler;
import com.project.instagram.service.tag.LocationTagService;
import com.project.instagram.web.dto.locationTag.ReadLocationTagResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LocationTagRestControllerTest {

    private Gson gson;
    private MockMvc mockMvc;
    private CustomExceptionHandler customExceptionHandler;
    @Mock
    private LocationTagService locationTagService;

    @InjectMocks
    private LocationTagRestController locationTagRestController;

    @BeforeEach
    void init() {
        gson = new Gson();
        customExceptionHandler = new CustomExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(locationTagRestController)
                .setControllerAdvice(customExceptionHandler)
                .build();
    }

    @Test
    void gson_mockMvc_isNotNull() {
        // then
        assertThat(gson).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void getLocationTagInformation_restController_호출성공() throws Exception {
        // givne
        String url = "/api/v1/location-tag/information/서";
        List<ReadLocationTagResponseDto> readLocationTagResponseDtoList = new ArrayList<>();
        readLocationTagResponseDtoList.add(new ReadLocationTagResponseDto(1L, "서면", 2L));

        when(locationTagService.getLocationTagInformation(anyString())).thenReturn(readLocationTagResponseDtoList);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print());
        resultActions.andExpect(jsonPath("$.data[0].totalCount").value(2L));
    }
}