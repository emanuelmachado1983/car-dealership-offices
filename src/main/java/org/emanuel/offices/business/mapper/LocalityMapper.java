package org.emanuel.offices.business.mapper;

import org.emanuel.offices.business.dto.LocalityGetDto;
import org.emanuel.offices.domain.Locality;
import org.springframework.stereotype.Component;

@Component
public class LocalityMapper {
    public LocalityGetDto toGetDto(Locality locality) {
        return LocalityGetDto.builder()
                .id(locality.getId())
                .name(locality.getName())
                .build();
    }
}
