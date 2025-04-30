package com.company.CompanyApp.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@Valid
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Construction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal plannedPrizeOfRealization;

    private double percentOfRealization;

    @Column(name = "current_cost_of_realization", nullable = false)
    private BigDecimal currentCostOfRealization = BigDecimal.ZERO;

    private String location;

    @OneToMany(mappedBy = "construction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Material> materials;

    @OneToMany(mappedBy = "construction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Customer> customers;

    @OneToMany(mappedBy = "construction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;



}