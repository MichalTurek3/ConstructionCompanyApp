package com.company.CompanyApp.dto;

import com.company.CompanyApp.domain.Construction;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MaterialDTO {

    private LocalDate orderDate;

    private BigDecimal price;

    private Construction construction;
}
