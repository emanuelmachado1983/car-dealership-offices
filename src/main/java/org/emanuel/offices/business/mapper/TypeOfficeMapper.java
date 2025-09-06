package org.emanuel.offices.business.mapper;

import org.emanuel.offices.business.dto.TypeOfficeDto;
import org.emanuel.offices.domain.TypeOffice;
import org.springframework.stereotype.Component;

@Component
public class TypeOfficeMapper {
    public TypeOfficeDto toDto(TypeOffice typeOffice) {
        return TypeOfficeDto.builder()
                .id(typeOffice.getId())
                .name(typeOffice.getName())
                .build();
    }
}
