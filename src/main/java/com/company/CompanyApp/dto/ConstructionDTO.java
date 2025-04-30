package com.company.CompanyApp.dto;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class ConstructionDTO {

    private String name;

    private String location;

    private BigDecimal plannedPrizeOfRealization;

    private double percentOfRealization;

    private BigDecimal currentCostOfRealization;

}
