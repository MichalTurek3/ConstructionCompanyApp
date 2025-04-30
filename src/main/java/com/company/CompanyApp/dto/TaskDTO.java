package com.company.CompanyApp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskDTO {

    private String name;

    private double duration;

    private boolean isDone;

    private BigDecimal plannedValue;

    private Long constructionId;
}
