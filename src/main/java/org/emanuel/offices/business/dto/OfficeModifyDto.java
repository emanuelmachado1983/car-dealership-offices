package org.emanuel.offices.business.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfficeModifyDto {
    private Long idCountry;
    private Long idProvince;
    private Long idLocality;
    private String address;
    private String name;
    private LocalDateTime openingDate;
    private Long typeOfficeId;

}
