package org.emanuel.offices.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "delivery_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliverySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "office_from_id", nullable = false)
    @JsonBackReference
    private Office officeFrom;

    @ManyToOne
    @JoinColumn(name = "office_to_id", nullable = false)
    @JsonBackReference
    private Office officeTo;

    @Column(name = "days", nullable = false)
    private Integer days;

}
