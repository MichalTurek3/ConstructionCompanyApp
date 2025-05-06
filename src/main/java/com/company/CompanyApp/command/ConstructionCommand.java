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

    @Size(min = 3, max = 50, message = "Location should contain between 3 to 20 letters")
    private String location;

    // Lista obiektow typu Contractor - > ContractCommand, DTO, Domain itp.
    // Lista obiektow typu Document -> String umowa nazwa pdf
    // Potrzebne materialy - Lista materialow


}
