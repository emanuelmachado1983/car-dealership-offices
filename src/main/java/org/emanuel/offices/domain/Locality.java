package org.emanuel.offices.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "localities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Locality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    @JsonBackReference
    private Province province;

}
