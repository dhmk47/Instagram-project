package com.project.instagram.service.tag;

import com.project.instagram.domain.board.Board;
import com.project.instagram.domain.tag.LocationTag;
import com.project.instagram.domain.tag.LocationTagRepository;
import com.project.instagram.web.dto.locationTag.ReadLocationTagResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationTagServiceImpl implements LocationTagService {

    private final LocationTagRepository locationTagRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void addLocationTag(Board board, List<String> locationTagNameList) {
        List<LocationTag> locationTagList =  locationTagNameList.stream()
                .map(locationTag -> {
                    return LocationTag.builder()
                            .board(board)
                            .tagName(locationTag)
                            .build();
                })
                .collect(Collectors.toList());

        locationTagRepository.saveAll(locationTagList);
    }

    @Override
    public List<ReadLocationTagResponseDto> getLocationTagInformation(String searchTagName) {
        String jpql = "select new com.project.instagram.web.dto.locationTag.ReadLocationTagResponseDto(l.tagCode, l.tagName, count(l.tagName)) from LocationTag l where l.tagName like '%" + searchTagName + "%'";
        return entityManager.createQuery(jpql, ReadLocationTagResponseDto.class).getResultList();
    }
}