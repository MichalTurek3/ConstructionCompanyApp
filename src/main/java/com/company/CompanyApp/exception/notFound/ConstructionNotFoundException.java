package com.company.CompanyApp.exception.notFound;

public class ConstructionNotFoundException extends RuntimeException{
    public ConstructionNotFoundException() {
    }

    public ConstructionNotFoundException(String message) {
        super(message);
    }
}
