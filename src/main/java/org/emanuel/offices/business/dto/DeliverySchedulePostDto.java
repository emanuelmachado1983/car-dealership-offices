package org.emanuel.offices.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliverySchedulePostDto {
    private Long officeFrom;
    private Long officeTo;
    private Integer days;

}
