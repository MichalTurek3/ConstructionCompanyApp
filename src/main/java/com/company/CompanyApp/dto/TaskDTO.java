package com.company.CompanyApp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskDTO {

    private String name;

    private Integer durationDay;

    private boolean isDone;

    private BigDecimal plannedValue;

    private Long constructionId;
}
