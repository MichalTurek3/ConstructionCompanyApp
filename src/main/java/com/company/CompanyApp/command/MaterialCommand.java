package com.company.CompanyApp.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MaterialCommand {

    @Size(min = 3, max = 20, message = "Material name should contain between 3 to 20 letters")
    private String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Price of material cannot be blank")
    private BigDecimal price;

    @Min(value = 1, message = "Price should not be zero")
    private int amount;

    @DateTimeFormat
    private LocalDate orderDate;

}
