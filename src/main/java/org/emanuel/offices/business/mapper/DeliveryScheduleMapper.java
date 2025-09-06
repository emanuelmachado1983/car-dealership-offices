package org.emanuel.offices.business.mapper;

import org.emanuel.offices.business.dto.DeliveryScheduleGetDto;
import org.emanuel.offices.domain.DeliverySchedule;
import org.springframework.stereotype.Component;

@Component
public class DeliveryScheduleMapper {
    private final OfficeMapper officeMapper;

    public DeliveryScheduleMapper(OfficeMapper officeMapper) {
        this.officeMapper = officeMapper;
    }

    public DeliveryScheduleGetDto toDto(DeliverySchedule deliverySchedule) {
        return DeliveryScheduleGetDto.builder()
                .id(deliverySchedule.getId())
                .officeFrom(officeMapper.toDto(deliverySchedule.getOfficeFrom()))
                .officeTo(officeMapper.toDto(deliverySchedule.getOfficeTo()))
                .days(deliverySchedule.getDays())
                .build();
    }
}
