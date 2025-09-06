package org.emanuel.offices.repository;

import org.emanuel.offices.domain.DeliverySchedule;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.domain.TypeOffice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IDeliveryScheduleDao extends CrudRepository<DeliverySchedule, Long> {
    @Override
    List<DeliverySchedule> findAll();

    @Query("SELECT d FROM DeliverySchedule d WHERE d.officeTo.id = :idOfficeTo")
    List<DeliverySchedule> findDeliveryScheduleByOfficeTo(@Param("idOfficeTo") Long idOffice);

    @Query("SELECT d FROM DeliverySchedule d WHERE d.officeFrom.id = :officeFrom AND d.officeTo.id = :officeTo")
    DeliverySchedule findDeliveryScheduleByOfficeFromAndOfficeTo(@Param("officeFrom") Long officeFrom, @Param("officeTo") Long officeTo);

    @Query("SELECT COUNT(d) > 0 FROM DeliverySchedule d WHERE d.officeFrom.id = :officeFrom AND d.officeTo.id = :officeTo")
    boolean existsByOfficeFromAndOfficeTo(@Param("officeFrom") Long officeFrom, @Param("officeTo") Long officeTo);
}
