package com.company.CompanyApp.repository;

import com.company.CompanyApp.domain.Construction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConstructionRepository extends JpaRepository<Construction, Long> {

    Optional<Construction> findConstructionByName(String name);

    Optional<Construction> findConstructionByLocation(String location);

    Page<Construction> findAllByCustomers_Id(Long id, Pageable pageable);


}
