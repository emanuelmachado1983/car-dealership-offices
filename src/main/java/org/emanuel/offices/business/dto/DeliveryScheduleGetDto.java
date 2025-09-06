package org.emanuel.offices.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DeliveryScheduleGetDto {
    private Long id;
    private OfficeGetDto officeFrom;
    private OfficeGetDto officeTo;
    private Integer days;

}
