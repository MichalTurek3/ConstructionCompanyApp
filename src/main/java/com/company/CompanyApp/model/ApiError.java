package com.company.CompanyApp.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {
    private HttpStatus httpStatus;
    private Object errors;
}