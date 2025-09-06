package org.emanuel.offices.business;

import org.emanuel.offices.business.dto.ProvinceGetDto;
import org.emanuel.offices.exceptions.CountryNotExistException;
import org.emanuel.offices.exceptions.ProvinceNotExistException;

import java.util.List;

public interface IProvinceService {
    ProvinceGetDto getProvinceById(Long idCountry, Long idProvince) throws ProvinceNotExistException;

    List<ProvinceGetDto> getAllProvinces(Long idCountry);

    ProvinceGetDto addProvince(Long idCountry, String name) throws CountryNotExistException;

    ProvinceGetDto updateProvince(Long idCountry, Long idProvince, String name) throws ProvinceNotExistException;

    void deleteProvince(Long idCountry, Long idProvince) throws ProvinceNotExistException, CountryNotExistException;
}
