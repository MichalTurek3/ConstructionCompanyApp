package com.company.CompanyApp.exception.notAuthenticated;

public class UserNotAuthenticatedException extends RuntimeException{
    public UserNotAuthenticatedException() {
    }

    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
