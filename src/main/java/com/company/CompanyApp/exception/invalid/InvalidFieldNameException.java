package com.company.CompanyApp.exception.invalid;

public class InvalidFieldNameException extends RuntimeException{
    public InvalidFieldNameException() {
    }

    public InvalidFieldNameException(String message) {
        super(message);
    }
}
