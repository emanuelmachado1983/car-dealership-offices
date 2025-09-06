package org.emanuel.offices.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProvinceGetDto {
    Long id;
    String name;
    Long countryId;

}
