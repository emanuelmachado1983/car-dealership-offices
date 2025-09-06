package org.emanuel.offices.business;

import org.emanuel.offices.business.dto.OfficeGetDto;
import org.emanuel.offices.business.dto.OfficeModifyDto;
import org.emanuel.offices.exceptions.OfficeBadRequestException;
import org.emanuel.offices.exceptions.OfficeLocalityNotExistsException;
import org.emanuel.offices.exceptions.OfficeNotExistsException;

import java.util.List;

public interface IOfficeService {
    OfficeGetDto getOfficeById(Long id) throws OfficeNotExistsException;

    Boolean officeExists(Long id);

    List<OfficeGetDto> getAllOffices();

    OfficeGetDto addOffice(OfficeModifyDto officeModifyDto) throws OfficeBadRequestException, OfficeLocalityNotExistsException;

    OfficeGetDto updateOffice(Long id, OfficeModifyDto officeModifyDto) throws OfficeNotExistsException, OfficeLocalityNotExistsException, OfficeBadRequestException;

    void deleteOffice(Long id) throws OfficeNotExistsException;

}
