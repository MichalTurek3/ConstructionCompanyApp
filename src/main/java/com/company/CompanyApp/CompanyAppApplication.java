package com.company.CompanyApp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class CompanyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyAppApplication.class, args);
	}

}
