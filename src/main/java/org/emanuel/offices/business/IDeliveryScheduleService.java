package org.emanuel.offices.business;

import org.emanuel.offices.business.dto.DeliveryScheduleGetDto;
import org.emanuel.offices.domain.DeliverySchedule;
import org.emanuel.offices.exceptions.DeliveryScheduleBadRequestException;
import org.emanuel.offices.exceptions.DeliveryScheduleNotExistsException;

import java.util.List;

public interface IDeliveryScheduleService {
    List<DeliveryScheduleGetDto> getAllDeliverySchedules();

    List<DeliveryScheduleGetDto> getAllDeliverySchedules(Long officeTo) throws DeliveryScheduleBadRequestException;

    void addDeliverySchedule(Long officeFrom, Long officeTo, Integer days) throws DeliveryScheduleBadRequestException;

    void deleteDeliverySchedule(Long id) throws DeliveryScheduleNotExistsException;
}
