package com.company.CompanyApp.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CustomerCommand {

    @Size(min = 3, max = 20, message = "Your first name should contain between 3 to 20 letters")
    private String firstName;

    @Size(min = 3, max = 20, message = "Your last name should contain between 3 to 20 letters")
    private String lastName;

    @Size(min = 3, max = 20, message = "Your username should contain between 3 to 20 letters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Size(min = 3, max = 20, message = "Your specialization should contain between 3 to 20 letters")
    private String specialization;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Hourly rate cannot be blank")
    private BigDecimal hourlyRate;

}