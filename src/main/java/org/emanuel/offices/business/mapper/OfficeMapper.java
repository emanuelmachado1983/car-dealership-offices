package org.emanuel.offices.business.mapper;

import org.emanuel.offices.business.dto.OfficeGetDto;
import org.emanuel.offices.business.dto.OfficeModifyDto;
import org.emanuel.offices.business.dto.TypeOfficeDto;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.domain.TypeOffice;
import org.springframework.stereotype.Component;

@Component
public class OfficeMapper {
    public OfficeGetDto toDto(Office office) {

        return OfficeGetDto.builder()
                .id(office.getId())
                .idCountry(office.getIdCountry())
                .idProvince(office.getIdProvince())
                .idLocality(office.getIdLocality())
                .address(office.getAddress())
                .name(office.getName())
                .openingDate(office.getOpeningDate())
                .typeOffice(TypeOfficeDto.builder()
                        .id(office.getTypeOffice().getId())
                        .name(office.getTypeOffice().getName())
                        .build()
                )
                .build();
    }

    public Office toModel(Long id, OfficeModifyDto officeModifyDto) {
        return Office.builder()
                .id(id)
                .idCountry(officeModifyDto.getIdCountry())
                .idProvince(officeModifyDto.getIdProvince())
                .idLocality(officeModifyDto.getIdLocality())
                .typeOffice(TypeOffice.builder()
                        .id(officeModifyDto.getTypeOfficeId())
                        .name(null)
                        .build())
                .address(officeModifyDto.getAddress())
                .name(officeModifyDto.getName())
                .openingDate(officeModifyDto.getOpeningDate())
                .build();
    }
}
