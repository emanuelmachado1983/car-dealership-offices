package org.emanuel.offices.business.impl;

import org.emanuel.offices.business.ICountryService;
import org.emanuel.offices.business.ILocalityService;
import org.emanuel.offices.business.IProvinceService;
import org.emanuel.offices.business.dto.ProvinceGetDto;
import org.emanuel.offices.business.mapper.CountryMapper;
import org.emanuel.offices.business.mapper.ProvinceMapper;
import org.emanuel.offices.domain.Province;
import org.emanuel.offices.exceptions.CountryNotExistException;
import org.emanuel.offices.exceptions.ProvinceNotExistException;
import org.emanuel.offices.repository.IProvinceDao;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProvinceServiceImpl implements IProvinceService {
    private final ICountryService countryService;
    private final CountryMapper countryMapper;
    private final IProvinceDao provinceDao;
    private final ProvinceMapper provinceMapper;
    private final ILocalityService localityService;

    public ProvinceServiceImpl(ICountryService countryService,
                               CountryMapper countryMapper,
                               IProvinceDao provinceDao,
                               ProvinceMapper provinceMapper,
                               @Lazy ILocalityService localityService
    ) {
        this.countryService = countryService;
        this.countryMapper = countryMapper;
        this.provinceDao = provinceDao;
        this.provinceMapper = provinceMapper;
        this.localityService = localityService;
    }

    @Override
    public ProvinceGetDto getProvinceById(Long idCountry, Long idProvince) throws ProvinceNotExistException {
        return provinceMapper.toGetDto(
                provinceDao.findByIdAndNotDeleted(idCountry, idProvince).orElseThrow(() -> new ProvinceNotExistException("Province not found with id: " + idProvince)));
    }

    @Override
    public List<ProvinceGetDto> getAllProvinces(Long idCountry) {
        return provinceDao.findAllAndNotDeleted(idCountry).stream().map(provinceMapper::toGetDto).toList();
    }

    @Override
    public ProvinceGetDto addProvince(Long idCountry, String name) throws CountryNotExistException {
        var country = countryService.getCountryById(idCountry);
        var province = new Province();
        province.setName(name);
        province.setCountry(countryMapper.toModel(country));
        return provinceMapper.toGetDto(provinceDao.save(province));
    }

    @Override
    public ProvinceGetDto updateProvince(Long idCountry, Long idProvince, String name) throws ProvinceNotExistException {
        var province = provinceDao.findByIdAndNotDeleted(idCountry, idProvince).orElseThrow(() -> new ProvinceNotExistException("Province not found with id: " + idProvince));
        province.setName(name);
        return provinceMapper.toGetDto(provinceDao.save(province));

    }

    @Override
    public void deleteProvince(Long idCountry, Long idProvince) throws ProvinceNotExistException, CountryNotExistException {
        var province = provinceDao.findByIdAndNotDeleted(idCountry, idProvince).orElseThrow(() -> new ProvinceNotExistException("Province not found with id: " + idProvince));
        if (!localityService.getAllLocalities(idCountry, idProvince).isEmpty()) {
            throw new ProvinceNotExistException("Cannot delete province with existing localities");
        }
        province.setDeletedAt(LocalDateTime.now());
        provinceDao.save(province);
    }
}
