package com.company.CompanyApp.dto;

import com.company.CompanyApp.model.CompanyRole;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerDTO {

    private String firstName;

    private String lastName;

    private String username;

    private String specialization;

    private BigDecimal hourlyRate;
}
