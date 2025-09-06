package org.emanuel.offices.business;

import org.emanuel.offices.business.dto.CountryGetDto;
import org.emanuel.offices.exceptions.CountryBadRequestException;
import org.emanuel.offices.exceptions.CountryNotExistException;

import java.util.List;

public interface ICountryService {
    CountryGetDto getCountryById(Long id) throws CountryNotExistException;

    List<CountryGetDto> getAllCountries();

    CountryGetDto addCountry(String name);

    CountryGetDto updateCountry(Long idCountry, String name) throws CountryNotExistException;

    void deleteCountry(Long id) throws CountryNotExistException, CountryBadRequestException;
}
