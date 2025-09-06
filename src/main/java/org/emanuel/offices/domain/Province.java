package org.emanuel.offices.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "provinces")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    @JsonBackReference
    private Country country;

    @OneToMany(mappedBy = "province")
    @JsonManagedReference
    private List<Locality> localities;

    public Province() {

    }

    public Province(Long id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
