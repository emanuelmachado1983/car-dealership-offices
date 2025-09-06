package org.emanuel.offices.business;

import org.emanuel.offices.business.dto.TypeOfficeDto;
import org.emanuel.offices.domain.TypeOffice;
import org.emanuel.offices.exceptions.TypeOfficeNotExistsException;

import java.util.List;

public interface ITypeOfficeService {
    List<TypeOfficeDto> getTypeOffices();

    TypeOfficeDto getTypeOfficeById(Long id) throws TypeOfficeNotExistsException;

    Boolean existsById(Long id);
}
