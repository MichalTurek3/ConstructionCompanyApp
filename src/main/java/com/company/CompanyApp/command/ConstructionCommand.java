package com.company.CompanyApp.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ConstructionCommand {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Planned prize of realization cannot be blank")
    private BigDecimal plannedPrizeOfRealization;

//    @PositiveOrZero(message = "Percent of realization must be positive or zero")
//    private double percentOfRealization;
//
//    @PositiveOrZero(message = "Current cost of realization must be positive or zero")
//    private double currentCostOfRealization;

    @Size(min = 3, max = 50, message = "Location should contain between 3 to 20 letters")
    private String location;

}
