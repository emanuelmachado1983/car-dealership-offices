package org.emanuel.offices.business.mapper;

import org.emanuel.offices.business.dto.ProvinceGetDto;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.Province;
import org.springframework.stereotype.Component;

@Component
public class ProvinceMapper {

    public ProvinceGetDto toGetDto(Province province) {
        return ProvinceGetDto.builder()
                .id(province.getId())
                .name(province.getName())
                .countryId(province.getCountry().getId())
                .build();
    }

    public Province toModel(ProvinceGetDto provinceGetDto) {
        return Province.builder()
                .id(provinceGetDto.getId())
                .name(provinceGetDto.getName())
                .country(Country.builder()
                        .id(provinceGetDto.getCountryId())
                        .build())
                .build();
    }

}
