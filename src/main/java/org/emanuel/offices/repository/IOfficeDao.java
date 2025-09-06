package org.emanuel.offices.repository;

import org.emanuel.offices.domain.Office;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IOfficeDao extends CrudRepository<Office, Long> {
    @Query("SELECT o FROM Office o WHERE o.id = :idOffice AND o.deletedAt IS NULL")
    Optional<Office> findByIdAndNotDeleted(@Param("idOffice") Long idOffice);

    @Query("SELECT o FROM Office o WHERE o.deletedAt IS NULL")
    List<Office> findAllAndNotDeleted();

    @Query("SELECT COUNT(o) > 0 FROM Office o WHERE o.id = :idOffice AND o.deletedAt IS NULL")
    boolean existsByIdAndNotDeleted(@Param("idOffice") Long idOffice);

    @Query("SELECT o FROM Office o WHERE o.idLocality = :idLocality AND o.deletedAt IS NULL")
    List<Office> findAllOfficesWithLocalityAndNotDeleted(@Param("idLocality") Long idLocality);
}
