package com.company.CompanyApp.exception.notFound;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException() {
    }

    public AdminNotFoundException(String message) {
        super(message);
    }
}
