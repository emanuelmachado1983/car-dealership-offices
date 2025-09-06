package org.emanuel.offices.repository;

import org.emanuel.offices.domain.TypeOffice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITypeOfficeDao extends CrudRepository<TypeOffice, Long> {
    @Override
    List<TypeOffice> findAll();
}
