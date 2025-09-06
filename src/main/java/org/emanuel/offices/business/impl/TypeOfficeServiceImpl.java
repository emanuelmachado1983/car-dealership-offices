package org.emanuel.offices.business.impl;

import org.emanuel.offices.business.ITypeOfficeService;
import org.emanuel.offices.business.dto.TypeOfficeDto;
import org.emanuel.offices.business.mapper.TypeOfficeMapper;
import org.emanuel.offices.exceptions.TypeOfficeNotExistsException;
import org.emanuel.offices.repository.ITypeOfficeDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeOfficeServiceImpl implements ITypeOfficeService {
    private final ITypeOfficeDao typeOfficeDao;
    private final TypeOfficeMapper typeOfficeMapper;

    public TypeOfficeServiceImpl(ITypeOfficeDao typeOfficeDao, TypeOfficeMapper typeOfficeMapper) {
        this.typeOfficeDao = typeOfficeDao;
        this.typeOfficeMapper = typeOfficeMapper;
    }

    @Override
    public List<TypeOfficeDto> getTypeOffices() {
        return typeOfficeDao.findAll().stream().map(typeOfficeMapper::toDto).toList();
    }

    @Override
    public TypeOfficeDto getTypeOfficeById(Long id) throws TypeOfficeNotExistsException {
        return typeOfficeMapper.toDto(
                typeOfficeDao.findById(id).orElseThrow(() -> new TypeOfficeNotExistsException("Office type with id " + id + " does not exist")));
    }

    @Override
    public Boolean existsById(Long id) {
        return typeOfficeDao.existsById(id);
    }
}
