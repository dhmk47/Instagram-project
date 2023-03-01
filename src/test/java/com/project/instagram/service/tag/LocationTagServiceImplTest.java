package com.project.instagram.service.tag;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.board.BoardType;
import com.project.instagram.domain.tag.LocationTag;
import com.project.instagram.domain.tag.LocationTagRepository;
import com.project.instagram.domain.user.User;
import com.project.instagram.web.dto.locationTag.ReadLocationTagResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocationTagServiceImplTest {

    @Mock
    private EntityManager entityManager;
    @Mock
    private TypedQuery typedQuery;
    @Mock
    private LocationTagRepository locationTagRepository;

    private LocationTagService locationTagService;

    @BeforeEach()
    void init() {
        locationTagService = new LocationTagServiceImpl(locationTagRepository, entityManager);
    }

    @Test
    @DisplayName("locationTag 등록설공")
    void addLocationTag() {
        // given
        List<String> locationTagList = new ArrayList<>(Arrays.asList("서면", "백엔드", "개발"));

        Board board = Board.builder()
                .user(mock(User.class))
                .boardType(mock(BoardType.class))
                .disableCommentFlag(0)
                .hideViewAndLikeCountFlag(0)
                .build();

        when(locationTagRepository.saveAll(anyList())).thenReturn(anyList());
        // when
        locationTagService.addLocationTag(board, locationTagList);

        // then
    }

    @Test
    @DisplayName("1건 이상의 locationTag 조회 성공")
    void locationTagLoadResultOne() {
        // given
        String locationTagName = "서";
        List<ReadLocationTagResponseDto> readLocationTagResponseDtoList = new ArrayList<>();
        readLocationTagResponseDtoList.add(new ReadLocationTagResponseDto(1L, "서면", 2L));

        when(entityManager.createQuery(anyString(), eq(ReadLocationTagResponseDto.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(readLocationTagResponseDtoList);
        // when
        List<ReadLocationTagResponseDto> readLocationTagResponseDtoResultList = locationTagService.getLocationTagInformation(locationTagName);

        // then
        assertThat(readLocationTagResponseDtoResultList.size()).isEqualTo(1);
        assertThat(readLocationTagResponseDtoResultList.get(0).getTagName()).isEqualTo("서면");
        assertThat(readLocationTagResponseDtoResultList.get(0).getTotalCount()).isEqualTo(2L);
    }

    @Test
    @DisplayName("0건의 LocationTag 정보는 수집 X")
    void locationTagLoadResultZero() {
        //given
        String locationTagName = "없음";
        List<ReadLocationTagResponseDto> readLocationTagResponseDtoList = new ArrayList<>();
        readLocationTagResponseDtoList.add(new ReadLocationTagResponseDto(null, null, 0L));

        when(entityManager.createQuery(anyString(), eq(ReadLocationTagResponseDto.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(readLocationTagResponseDtoList);

        // when
        List<ReadLocationTagResponseDto> readLocationTagResponseDtoResultList = locationTagService.getLocationTagInformation(locationTagName);

        // then
        assertThat(readLocationTagResponseDtoResultList).hasSize(0);
    }
}