package com.company.CompanyApp.exception.invalid;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException() {
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
