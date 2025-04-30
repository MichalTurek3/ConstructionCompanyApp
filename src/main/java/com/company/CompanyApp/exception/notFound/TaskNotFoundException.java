package com.company.CompanyApp.exception.notFound;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
