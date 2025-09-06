package org.emanuel.offices.repository;

import org.emanuel.offices.domain.Province;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IProvinceDao extends CrudRepository<Province, Long> {
    @Query("SELECT p FROM Province p WHERE p.country.id = :idCountry AND p.id = :idProvince AND p.deletedAt IS NULL")
    Optional<Province> findByIdAndNotDeleted(@Param("idCountry") Long idCountry, @Param("idProvince") Long idProvince);

    @Query("SELECT p FROM Province p WHERE p.country.id = :idCountry AND p.deletedAt IS NULL")
    List<Province> findAllAndNotDeleted(@Param("idCountry") Long idCountry);

}
