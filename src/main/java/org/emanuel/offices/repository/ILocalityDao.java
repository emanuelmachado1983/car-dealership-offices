package org.emanuel.offices.repository;

import org.emanuel.offices.domain.Locality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ILocalityDao extends CrudRepository<Locality, Long> {
    @Query("SELECT l FROM Locality l WHERE l.id = :idLocality AND l.province.id = :idProvince AND l.deletedAt IS NULL")
    Optional<Locality> findByIdAndNotDeleted(@Param("idProvince") Long idProvince, @Param("idLocality") Long idLocality);

    @Query("SELECT l FROM Locality l WHERE l.province.id = :idProvince AND l.deletedAt IS NULL")
    List<Locality> findAllAndNotDeleted(@Param("idProvince") Long idProvince);
}
