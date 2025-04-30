package com.company.CompanyApp.repository;

import com.company.CompanyApp.domain.Action;
import com.company.CompanyApp.domain.Admin;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
    @NotNull Page<Action> findAll(@NotNull Pageable pageable);

    Page<Action> findByCreatedBy(Admin admin, Pageable pageable);
}
