package org.emanuel.offices.business.impl;

import org.emanuel.offices.business.ILocalityService;
import org.emanuel.offices.business.IOfficeService;
import org.emanuel.offices.business.ITypeOfficeService;
import org.emanuel.offices.business.dto.OfficeGetDto;
import org.emanuel.offices.business.dto.OfficeModifyDto;
import org.emanuel.offices.business.mapper.OfficeMapper;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.exceptions.*;
import org.emanuel.offices.repository.IOfficeDao;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficeServiceImpl implements IOfficeService {
    private final ITypeOfficeService typeOfficeService;
    private final IOfficeDao officeDao;
    private final ILocalityService localityService;
    private final OfficeMapper officeMapper;

    private final static long ID_CENTRAL = 1;

    public OfficeServiceImpl(ITypeOfficeService typeOfficeService,
                             IOfficeDao officeDao,
                             @Lazy ILocalityService localityService,
                             OfficeMapper officeMapper) {
        this.typeOfficeService = typeOfficeService;
        this.officeDao = officeDao;
        this.localityService = localityService;
        this.officeMapper = officeMapper;
    }

    @Override
    public OfficeGetDto getOfficeById(Long id) throws OfficeNotExistsException {
        return officeMapper.toDto(
                officeDao.findByIdAndNotDeleted(id).orElseThrow(() -> new OfficeNotExistsException("Office with id " + id + " does not exist or has been deleted")));
    }

    @Override
    public Boolean officeExists(Long id) {
        return officeDao.existsByIdAndNotDeleted(id);
    }

    @Override
    public List<OfficeGetDto> getAllOffices() {
        return officeDao.findAllAndNotDeleted().stream().map(officeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public OfficeGetDto addOffice(OfficeModifyDto officeModifyDto) throws OfficeBadRequestException, OfficeLocalityNotExistsException {
        var office = officeMapper.toModel(null, officeModifyDto);
        if (office.getTypeOffice().getId() == ID_CENTRAL) {
            throw new OfficeBadRequestException("You cannot create a new office with type 'Central'.");
        }
        validations(office);
        var auxOffices = officeDao.findAllOfficesWithLocalityAndNotDeleted(office.getIdLocality());
        if (!auxOffices.isEmpty()) {
            throw new OfficeBadRequestException("There is already an office in the locality with id: " + office.getIdLocality());
        }

        if (office.getOpeningDate().isAfter(LocalDateTime.now())) {
            throw new OfficeBadRequestException("Opening date cannot be in the future.");
        }
        return officeMapper.toDto(officeDao.save(office));
    }

    @Override
    public OfficeGetDto updateOffice(Long id, OfficeModifyDto officeModifyDto) throws OfficeNotExistsException, OfficeLocalityNotExistsException, OfficeBadRequestException {
        var office = officeMapper.toModel(id, officeModifyDto);
        var officeBefore = officeDao.findByIdAndNotDeleted(office.getId()).orElseThrow(() -> new OfficeNotExistsException("Office with id " + office.getId() + " does not exist or has been deleted"));
        validations(office);
        if (!officeBefore.getTypeOffice().getId().equals(ID_CENTRAL)
                && office.getTypeOffice().getId().equals(ID_CENTRAL)) {
            throw new OfficeBadRequestException("You cannot change an office as a central office");
        }

        if (!office.getTypeOffice().getId().equals(ID_CENTRAL)
                && officeBefore.getTypeOffice().getId().equals(ID_CENTRAL)) {
            throw new OfficeBadRequestException("You cannot change an office as a central office");
        }

        return officeMapper.toDto(officeDao.save(office));
    }

    @Override
    public void deleteOffice(Long id) throws OfficeNotExistsException {
        var office = officeDao.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new OfficeNotExistsException("Office with id " + id + " does not exist or has been deleted"));

        office.setDeletedAt(LocalDateTime.now());
        officeDao.save(office);
    }

    private void validations(Office office) throws OfficeBadRequestException, OfficeLocalityNotExistsException {
        if (office.getTypeOffice() == null) {
            throw new OfficeBadRequestException("Should provide a type of office");
        }
        if (!typeOfficeService.existsById(office.getTypeOffice().getId())) {
            throw new OfficeBadRequestException("Non existent type of office: " + office.getTypeOffice().getId());
        }
        if (office.getIdCountry() == null) {
            throw new OfficeBadRequestException("Should provide a country id");
        }
        if (office.getIdProvince() == null) {
            throw new OfficeBadRequestException("Should provide a province id");
        }
        if (office.getIdLocality() == null) {
            throw new OfficeBadRequestException("Should provide a locality id");
        }
        if (!localityService.existsLocalityById(office.getIdCountry(), office.getIdProvince(), office.getIdLocality())) {
            throw new OfficeLocalityNotExistsException("Non existent locality: " + office.getIdLocality());
        }
    }
}
