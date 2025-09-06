package org.emanuel.offices.business.impl;

import org.emanuel.offices.business.ICountryService;
import org.emanuel.offices.business.ILocalityService;
import org.emanuel.offices.business.IOfficeService;
import org.emanuel.offices.business.IProvinceService;
import org.emanuel.offices.business.dto.LocalityGetDto;
import org.emanuel.offices.business.mapper.LocalityMapper;
import org.emanuel.offices.business.mapper.ProvinceMapper;
import org.emanuel.offices.domain.Locality;
import org.emanuel.offices.exceptions.CountryNotExistException;
import org.emanuel.offices.exceptions.LocalityBadRequestException;
import org.emanuel.offices.exceptions.LocalityNotExistException;
import org.emanuel.offices.exceptions.ProvinceNotExistException;
import org.emanuel.offices.repository.ILocalityDao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LocalityServiceImpl implements ILocalityService {
    private final ILocalityDao localityDao;
    private final IProvinceService provinceService;
    private final ICountryService countryService;
    private final ProvinceMapper provinceMapper;
    private final LocalityMapper localityMapper;
    private final IOfficeService officeService;

    public LocalityServiceImpl(ILocalityDao localityDao,
                               IProvinceService provinceService,
                               ICountryService countryService,
                               ProvinceMapper provinceMapper,
                               LocalityMapper localityMapper,
                               IOfficeService officeService) {
        this.localityDao = localityDao;
        this.provinceService = provinceService;
        this.countryService = countryService;
        this.provinceMapper = provinceMapper;
        this.localityMapper = localityMapper;
        this.officeService = officeService;
    }

    @Override
    public LocalityGetDto getLocalityById(Long idCountry, Long idProvince, Long idLocality) throws CountryNotExistException, LocalityNotExistException {
        countryService.getCountryById(idCountry);
        return localityMapper.toGetDto(
                localityDao.findByIdAndNotDeleted(idProvince, idLocality).orElseThrow(() -> new LocalityNotExistException("Locality doesn't exists with id: " + idLocality + " in province with id: " + idProvince)));
    }

    @Override
    public List<LocalityGetDto> getAllLocalities(Long idCountry, Long idProvince) throws CountryNotExistException {
        countryService.getCountryById(idCountry);
        return localityDao.findAllAndNotDeleted(idProvince).stream().map(localityMapper::toGetDto).toList();
    }

    @Override
    public LocalityGetDto addLocality(Long idCountry, Long idProvince, String name) throws CountryNotExistException, ProvinceNotExistException {
        countryService.getCountryById(idCountry);
        var province = provinceService.getProvinceById(idCountry, idProvince);
        var locality = Locality.builder()
                .name(name)
                .province(provinceMapper.toModel(province))
                .build();

        return localityMapper.toGetDto(localityDao.save(locality));
    }

    @Override
    public LocalityGetDto updateLocality(Long idCountry, Long idProvince, Long idLocality, String name) throws CountryNotExistException, ProvinceNotExistException, LocalityNotExistException {
        countryService.getCountryById(idCountry);
        provinceService.getProvinceById(idCountry, idProvince);
        var locality = localityDao.findByIdAndNotDeleted(idProvince, idLocality)
                .orElseThrow(() -> new LocalityNotExistException("Locality doesn't exists with id: " + idLocality + " in province with id: " + idProvince));
        locality.setName(name);
        return localityMapper.toGetDto(localityDao.save(locality));
    }

    @Override
    public void deleteLocality(Long idCountry, Long idProvince, Long idLocality) throws CountryNotExistException, ProvinceNotExistException, LocalityNotExistException, LocalityBadRequestException {
        countryService.getCountryById(idCountry);
        provinceService.getProvinceById(idCountry, idProvince);
        var locality = localityDao.findByIdAndNotDeleted(idProvince, idLocality)
                .orElseThrow(() -> new LocalityNotExistException("Locality doesn't exists with id: " + idLocality + " in province with id: " + idProvince));
        var office = officeService.getAllOffices();
        if (office.stream().anyMatch(o -> o.getIdLocality().equals(idLocality))) {
            throw new LocalityBadRequestException("Cannot delete locality with id: " + idLocality + " because it is associated with existing offices.");
        }

        locality.setDeletedAt(LocalDateTime.now());
        localityDao.save(locality);
    }

    @Override
    public Boolean existsLocalityById(Long idCountry, Long idProvince, Long idLocality) {
        var locality = localityDao.findByIdAndNotDeleted(idProvince, idLocality);
        return locality.isPresent();
    }
}
