package com.company.CompanyApp.command;

import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TaskCommand {

    @Size(min = 3, max = 20, message = "Material name should contain between 3 to 20 letters")
    private String name;

    @Min(value = 1, message = "Duration must be expressed in working days. 1 day = 8 hour")
    private int durationDay;

    @BooleanFlag
    private boolean isDone;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Planned value of task cannot be blank")
    private BigDecimal plannedValue;

    @NotNull
    private Long constructionId;
}
