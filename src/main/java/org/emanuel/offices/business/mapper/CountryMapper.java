package org.emanuel.offices.business.mapper;

import org.emanuel.offices.business.dto.CountryGetDto;
import org.emanuel.offices.domain.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public CountryGetDto toGetDto(Country country) {
        return new CountryGetDto(country.getId(), country.getName());
    }

    public Country toModel(CountryGetDto countryGetDto) {
        return Country.builder().id(countryGetDto.getId()).name(countryGetDto.getName()).build();
    }
}
