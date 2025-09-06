package org.emanuel.offices.unit.business;

import org.emanuel.offices.business.dto.CountryGetDto;
import org.emanuel.offices.business.dto.ProvinceGetDto;
import org.emanuel.offices.business.impl.CountryServiceImpl;
import org.emanuel.offices.business.mapper.CountryMapper;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.Province;
import org.emanuel.offices.exceptions.CountryBadRequestException;
import org.emanuel.offices.exceptions.CountryNotExistException;
import org.emanuel.offices.repository.ICountryDao;
import org.emanuel.offices.business.IProvinceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryServiceImplTest {
    @Mock
    private ICountryDao countryDao;
    @Mock
    private CountryMapper countryMapper;
    @Mock
    private IProvinceService provinceService;

    @InjectMocks
    private CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCountryById_returnsCountry() throws CountryNotExistException {
        Country country = Country.builder().id(1L).name("Argentina").build();
        CountryGetDto dto = CountryGetDto.builder().build();
        when(countryDao.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(country));
        when(countryMapper.toGetDto(country)).thenReturn(dto);

        CountryGetDto result = countryService.getCountryById(1L);
        assertEquals(dto, result);
    }

    @Test
    void getCountryById_throwsException() {
        when(countryDao.findByIdAndNotDeleted(1L)).thenReturn(Optional.empty());
        assertThrows(CountryNotExistException.class, () -> countryService.getCountryById(1L));
    }

    @Test
    void getAllCountries_returnsList() {
        Country country = Country.builder().id(1L).name("Argentina").build();
        CountryGetDto dto = CountryGetDto.builder().build();
        when(countryDao.findAllAndNotDeleted()).thenReturn(List.of(country));
        when(countryMapper.toGetDto(country)).thenReturn(dto);

        List<CountryGetDto> result = countryService.getAllCountries();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void getAllCountries_returnsEmptyList() {
        when(countryDao.findAllAndNotDeleted()).thenReturn(Collections.emptyList());
        List<CountryGetDto> result = countryService.getAllCountries();
        assertTrue(result.isEmpty());
    }

    @Test
    void addCountry_savesAndReturnsDto() {
        Country country = Country.builder().id(1L).name("Chile").build();
        CountryGetDto dto = CountryGetDto.builder().build();
        when(countryDao.save(any(Country.class))).thenReturn(country);
        when(countryMapper.toGetDto(country)).thenReturn(dto);

        CountryGetDto result = countryService.addCountry("Chile");
        assertEquals(dto, result);
        verify(countryDao).save(any(Country.class));
    }

    @Test
    void updateCountry_success() throws CountryNotExistException {
        Country country = Country.builder().id(1L).name("Uruguay").build();
        CountryGetDto dto = CountryGetDto.builder().build();
        when(countryDao.existsByIdAndNotDeleted(1L)).thenReturn(true);
        when(countryDao.save(any(Country.class))).thenReturn(country);
        when(countryMapper.toGetDto(country)).thenReturn(dto);

        CountryGetDto result = countryService.updateCountry(1L, "Uruguay");
        assertEquals(dto, result);
    }

    @Test
    void updateCountry_notExist_throwsException() {
        when(countryDao.existsByIdAndNotDeleted(1L)).thenReturn(false);
        assertThrows(CountryNotExistException.class, () -> countryService.updateCountry(1L, "Uruguay"));
    }

    @Test
    void deleteCountry_success() {
        Country country = Country.builder().id(1L).name("Paraguay").build();
        when(countryDao.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(country));
        when(provinceService.getAllProvinces(1L)).thenReturn(Collections.emptyList());
        when(countryDao.save(any(Country.class))).thenReturn(country);

        assertDoesNotThrow(() -> countryService.deleteCountry(1L));
        assertNotNull(country.getDeletedAt());
        verify(countryDao).save(country);
    }

    @Test
    void deleteCountry_notExist_throwsException() {
        when(countryDao.findByIdAndNotDeleted(1L)).thenReturn(Optional.empty());
        assertThrows(CountryNotExistException.class, () -> countryService.deleteCountry(1L));
    }

    @Test
    void deleteCountry_withProvinces_throwsBadRequest() {
        Country country = Country.builder().id(1L).name("Brasil").build();
        when(countryDao.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(country));
        when(provinceService.getAllProvinces(1L)).thenReturn(
                List.of(ProvinceGetDto.builder().id(1L).name("name").build())
        );

        assertThrows(CountryBadRequestException.class, () -> countryService.deleteCountry(1L));
    }
}

