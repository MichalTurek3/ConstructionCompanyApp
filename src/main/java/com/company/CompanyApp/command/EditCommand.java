package com.company.CompanyApp.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class EditCommand {

    @Size(min = 1, max = 30, message = "Your fieldName value should contain between 1 to 30 letters")
    String fieldName;

    @Size(min = 1, max = 30, message = "Your new value should contain between 1 to 30 letters")
    String newValue;
}