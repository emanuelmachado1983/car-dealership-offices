package org.emanuel.offices.business.impl;

import org.emanuel.offices.business.ICountryService;
import org.emanuel.offices.business.IProvinceService;
import org.emanuel.offices.business.dto.CountryGetDto;
import org.emanuel.offices.business.mapper.CountryMapper;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.exceptions.CountryBadRequestException;
import org.emanuel.offices.exceptions.CountryNotExistException;
import org.emanuel.offices.repository.ICountryDao;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements ICountryService {
    private final ICountryDao countryDao;
    private final CountryMapper countryMapper;
    private final IProvinceService provinceService;

    public CountryServiceImpl(ICountryDao countryDao, CountryMapper countryMapper,
                              @Lazy IProvinceService provinceService) {
        this.countryDao = countryDao;
        this.countryMapper = countryMapper;
        this.provinceService = provinceService;
    }

    @Override
    public CountryGetDto getCountryById(Long id) throws CountryNotExistException {
        return countryMapper.toGetDto(
                countryDao.findByIdAndNotDeleted(id).orElseThrow(() -> new CountryNotExistException("Country not found with id: " + id)));
    }

    @Override
    public List<CountryGetDto> getAllCountries() {
        return countryDao.findAllAndNotDeleted().stream().map(countryMapper::toGetDto).collect(Collectors.toList());
    }

    @Override
    public CountryGetDto addCountry(String name) {
        return countryMapper.toGetDto(countryDao.save(Country.builder().name(name).build()));
    }

    @Override
    public CountryGetDto updateCountry(Long idCountry, String name) throws CountryNotExistException {
        if (countryDao.existsByIdAndNotDeleted(idCountry)) {
            return countryMapper.toGetDto(countryDao.save(Country.builder().id(idCountry).name(name).build()));
        } else {
            throw new CountryNotExistException("Country not found with id: " + idCountry);
        }
    }

    @Override
    public void deleteCountry(Long id) throws CountryNotExistException, CountryBadRequestException {
        var country = countryDao.findByIdAndNotDeleted(id).orElseThrow(() -> new CountryNotExistException("Country not found with id: " + id));
        if (!provinceService.getAllProvinces(country.getId()).isEmpty()) {
            throw new CountryBadRequestException("Cannot delete country with id: " + id + " because it has associated provinces.");
        };
        country.setDeletedAt(LocalDateTime.now());
        countryDao.save(country);
    }
}
