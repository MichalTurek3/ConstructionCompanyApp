package com.company.CompanyApp.exception.notFound;

public class MaterialNotFoundException extends RuntimeException{
    public MaterialNotFoundException() {
    }

    public MaterialNotFoundException(String message) {
        super(message);
    }
}
