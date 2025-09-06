package org.emanuel.offices.business;

import org.emanuel.offices.business.dto.LocalityGetDto;
import org.emanuel.offices.exceptions.CountryNotExistException;
import org.emanuel.offices.exceptions.LocalityBadRequestException;
import org.emanuel.offices.exceptions.LocalityNotExistException;
import org.emanuel.offices.exceptions.ProvinceNotExistException;

import java.util.List;

public interface ILocalityService {
    LocalityGetDto getLocalityById(Long idCountry, Long idProvince, Long idLocality) throws CountryNotExistException, LocalityNotExistException;

    List<LocalityGetDto> getAllLocalities(Long idCountry, Long idProvince) throws CountryNotExistException;

    LocalityGetDto addLocality(Long idCountry, Long idProvince, String name) throws CountryNotExistException, ProvinceNotExistException;

    LocalityGetDto updateLocality(Long idCountry, Long idProvince, Long idLocality, String name) throws CountryNotExistException, ProvinceNotExistException, LocalityNotExistException;

    void deleteLocality(Long idCountry, Long idProvince, Long idLocality) throws CountryNotExistException, ProvinceNotExistException, LocalityNotExistException, LocalityBadRequestException;

    Boolean existsLocalityById(Long idCountry, Long idProvince, Long idLocality);
}
