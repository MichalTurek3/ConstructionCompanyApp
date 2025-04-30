package com.company.CompanyApp.exception.notFound;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException() {
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
