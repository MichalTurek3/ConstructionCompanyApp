package com.company.CompanyApp.repository;

import com.company.CompanyApp.domain.Material;
import liquibase.pro.packaged.M;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    Optional<Material> findMaterialByName(String name);

    Optional<Material> findMaterialByOrderDate(LocalDate orderDate);

    Page<Material> findAllByOrderDateIsBetween(LocalDate orderDateAfter, LocalDate orderDateBefore, Pageable pageable);

}
