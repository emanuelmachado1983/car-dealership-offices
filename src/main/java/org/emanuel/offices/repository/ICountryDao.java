package org.emanuel.offices.repository;

import org.emanuel.offices.domain.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICountryDao extends CrudRepository<Country, Long> {
    @Query("SELECT c FROM Country c WHERE c.id = :idCountry AND c.deletedAt IS NULL")
    Optional<Country> findByIdAndNotDeleted(@Param("idCountry") Long idCountry);

    @Query("SELECT c FROM Country c WHERE c.deletedAt IS NULL")
    List<Country> findAllAndNotDeleted();

    @Query("SELECT COUNT(c) > 0 FROM Country c WHERE c.id = :idCountry AND c.deletedAt IS NULL")
    boolean existsByIdAndNotDeleted(@Param("idCountry") Long idCountry);

}
