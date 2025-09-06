package org.emanuel.offices.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "type_offices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

}
