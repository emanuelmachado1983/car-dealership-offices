package org.emanuel.offices.business.impl;

import jakarta.transaction.Transactional;
import org.emanuel.offices.business.IDeliveryScheduleService;
import org.emanuel.offices.business.IOfficeService;
import org.emanuel.offices.business.dto.DeliveryScheduleGetDto;
import org.emanuel.offices.business.mapper.DeliveryScheduleMapper;
import org.emanuel.offices.domain.DeliverySchedule;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.exceptions.DeliveryScheduleBadRequestException;
import org.emanuel.offices.exceptions.DeliveryScheduleNotExistsException;
import org.emanuel.offices.repository.IDeliveryScheduleDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryScheduleServiceImpl implements IDeliveryScheduleService {
    private final IDeliveryScheduleDao deliveryScheduleDao;
    private final IOfficeService officeService;
    private final DeliveryScheduleMapper deliveryScheduleMapper;

    public DeliveryScheduleServiceImpl(IDeliveryScheduleDao deliveryScheduleDao,
                                       IOfficeService officeService,
                                       DeliveryScheduleMapper deliveryScheduleMapper) {
        this.deliveryScheduleDao = deliveryScheduleDao;
        this.officeService = officeService;
        this.deliveryScheduleMapper = deliveryScheduleMapper;
    }

    @Override
    public List<DeliveryScheduleGetDto> getAllDeliverySchedules() {
        return deliveryScheduleDao.findAll().stream().map(deliveryScheduleMapper::toDto).toList();
    }

    @Override
    public List<DeliveryScheduleGetDto> getAllDeliverySchedules(Long officeTo) throws DeliveryScheduleBadRequestException {
        if (!officeService.officeExists(officeTo)) {
            throw new DeliveryScheduleBadRequestException("Office with id " + officeTo + " does not exist.");
        }
        return deliveryScheduleDao.findDeliveryScheduleByOfficeTo(officeTo).stream().map(deliveryScheduleMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void addDeliverySchedule(Long officeFrom, Long officeTo, Integer days) throws DeliveryScheduleBadRequestException {
        if (!officeService.officeExists(officeFrom) || !officeService.officeExists(officeTo)) {
            throw new DeliveryScheduleBadRequestException("One or both offices do not exist.");
        }
        if (days <= 0) {
            throw new DeliveryScheduleBadRequestException("Days must be greater than zero.");
        }
        if (deliveryScheduleDao.existsByOfficeFromAndOfficeTo(officeFrom, officeTo)) {
            throw new DeliveryScheduleBadRequestException("Delivery schedule already exists for the given offices.");
        }
        var officeFromObj = new Office();
        officeFromObj.setId(officeFrom);
        var officeToObj = new Office();
        officeToObj.setId(officeTo);
        DeliverySchedule deliverySchedule = new DeliverySchedule();
        deliverySchedule.setOfficeFrom(officeFromObj);
        deliverySchedule.setOfficeTo(officeToObj);
        deliverySchedule.setDays(days);
        deliveryScheduleDao.save(deliverySchedule);
    }

    @Override
    public void deleteDeliverySchedule(Long id) throws DeliveryScheduleNotExistsException {
        deliveryScheduleDao.findById(id).orElseThrow(() -> new DeliveryScheduleNotExistsException("Delivery schedule with id " + id + " does not exist."));
        deliveryScheduleDao.deleteById(id);
    }
}
