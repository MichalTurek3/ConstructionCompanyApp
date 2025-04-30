package com.company.CompanyApp.repository;

import com.company.CompanyApp.domain.Construction;
import com.company.CompanyApp.domain.Customer;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByUsername(String username);

    Page<Customer> findAllByConstructionId(Long constructionId, Pageable pageable);

    @NotNull Page<Customer> findAll(@NotNull Pageable pageable);
}
